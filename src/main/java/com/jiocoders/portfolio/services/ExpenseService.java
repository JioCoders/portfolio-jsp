package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.ExpenseDao;
import com.jiocoders.portfolio.dto.*;
import com.jiocoders.portfolio.entity.*;
import com.jiocoders.portfolio.mappers.ExpenseMapper;
import com.jiocoders.portfolio.mappers.GroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

	private final ExpenseDao expenseDao;

	private final com.jiocoders.portfolio.dao.UserDao userDao;

	private final GroupMapper groupMapper;

	private final ExpenseMapper expenseMapper;

	/**
	 * compute net balance per user in a group Net Balance = (Total Paid) - (Total Share)
	 * + (Settlements Received) - (Settlements Paid)
	 */
	public Map<Long, BigDecimal> calculateBalances(Long groupId) {
		log.info("Calculating balances for group ID: {}", groupId);
		Map<Long, BigDecimal> balances = new HashMap<>();

		// 1. Process Expense Distributions
		// Formula: Net Balance = (Share Amount - Paid Amount) + Settlements
		List<ExpenseDistribution> distributions = expenseDao.findDistributionsByGroup(groupId);
		for (ExpenseDistribution dist : distributions) {
			Long userId = dist.getUser().getId();
			// If share > paid, user is owed money (positive)
			// If paid > share, user owes money (negative)
			BigDecimal net = dist.getShareAmount().subtract(dist.getPaidAmount());
			balances.put(userId, balances.getOrDefault(userId, BigDecimal.ZERO).add(net));
		}

		// 2. Adjust for Settlements
		// When user A pays user B: A's balance decreases, B's balance increases
		List<Settlement> settlements = expenseDao.findSettlementsByGroup(groupId);
		for (Settlement settle : settlements) {
			Long fromId = settle.getFromUser().getId();
			Long toId = settle.getToUser().getId();
			BigDecimal amount = settle.getAmount();

			// Payer (from) reduces their debt (balance decreases)
			balances.put(fromId, balances.getOrDefault(fromId, BigDecimal.ZERO).subtract(amount));
			// Receiver (to) increases their credit (balance increases)
			balances.put(toId, balances.getOrDefault(toId, BigDecimal.ZERO).add(amount));
		}

		log.info("Final calculated balances: {}", balances);
		return balances;
	}

	@Transactional
	public GroupDTO createGroup(GroupDTO groupDTO) {
		User creator = userDao.findById(groupDTO.getCreatedBy())
			.orElseThrow(() -> new RuntimeException("User not found: " + groupDTO.getCreatedBy()));

		Group group = Group.builder()
			.name(groupDTO.getName())
			.description(groupDTO.getDescription())
			.creator(creator)
			.build();

		Group savedGroup = expenseDao.saveGroup(group);

		// Add creator as member automaticallyx
		GroupMember member = GroupMember.builder().group(savedGroup).user(creator).role("ADMIN").build();
		expenseDao.addMemberToGroup(member);

		// Add other members if provided
		if (groupDTO.getMembers() != null) {
			for (UserDTO userDTO : groupDTO.getMembers()) {
				if (!userDTO.getId().equals(creator.getId())) {
					User user = userDao.findById(userDTO.getId())
						.orElseThrow(() -> new RuntimeException("User not found: " + userDTO.getId()));
					expenseDao
						.addMemberToGroup(GroupMember.builder().group(savedGroup).user(user).role("MEMBER").build());
				}
			}
		}

		// Refresh group to get members
		return groupMapper.toDTO(savedGroup);
	}

	public GroupDTO getGroup(Long id) {
		Group group = expenseDao.findGroupById(id).orElseThrow(() -> new RuntimeException("Group not found: " + id));
		return groupMapper.toDTO(group);
	}

	public List<GroupDTO> getUserGroups(Long userId) {
		List<Group> groups = expenseDao.findAllGroupsByUser(userId);
		return groupMapper.toDTOs(groups);
	}

	@Transactional
	public ExpenseDTO addExpense(Long groupId, ExpenseDTO expenseDTO) {
		Group group = expenseDao.findGroupById(groupId)
			.orElseThrow(() -> new RuntimeException("Group not found: " + groupId));

		// Validate all users are group members
		List<GroupMember> groupMembers = expenseDao.findMembersByGroup(groupId);
		List<Long> memberIds = groupMembers.stream().map(gm -> gm.getUser().getId()).toList();

		if (expenseDTO.getSplits() != null) {
			for (ExpenseSplitDTO splitDTO : expenseDTO.getSplits()) {
				if (!memberIds.contains(splitDTO.getUserId())) {
					throw new RuntimeException("User ID " + splitDTO.getUserId() + " is not a member of this group");
				}
			}
		}

		Expense expense = Expense.builder()
			.group(group)
			.title(expenseDTO.getTitle())
			.description(expenseDTO.getDescription())
			.totalAmount(expenseDTO.getTotalAmount())
			.currency(expenseDTO.getCurrency() != null ? expenseDTO.getCurrency() : "INR")
			.expenseDate(expenseDTO.getExpenseDate())
			.build();

		Expense savedExpense = expenseDao.saveExpense(expense);

		// Add splits (Distributions)
		if (expenseDTO.getSplits() != null) {
			BigDecimal totalShare = BigDecimal.ZERO;
			for (ExpenseSplitDTO splitDTO : expenseDTO.getSplits()) {
				User user = userDao.findById(splitDTO.getUserId())
					.orElseThrow(() -> new RuntimeException("User not found: " + splitDTO.getUserId()));

				ExpenseDistribution dist = ExpenseDistribution.builder()
					.expense(savedExpense)
					.user(user)
					.paidAmount(splitDTO.getPaidAmount() != null ? splitDTO.getPaidAmount() : BigDecimal.ZERO)
					.shareAmount(splitDTO.getShareAmount() != null ? splitDTO.getShareAmount() : BigDecimal.ZERO)
					.build();

				savedExpense.getDistributions().add(dist);
				totalShare = totalShare.add(dist.getShareAmount());
			}

			// Validate splits sum equals total amount
			if (totalShare.compareTo(savedExpense.getTotalAmount()) != 0) {
				throw new RuntimeException("Sum of share amounts (" + totalShare
						+ ") does not match total expense amount (" + savedExpense.getTotalAmount() + ")");
			}

			expenseDao.saveExpense(savedExpense); // Trigger cascade save for
													// distributions
		}

		return expenseMapper.toDTO(savedExpense);
	}

	public List<ExpenseDTO> getExpenses(Long groupId) {
		List<Expense> expenses = expenseDao.findExpensesByGroup(groupId);
		return expenseMapper.toDTOs(expenses);
	}

	public List<BalanceDTO> getBalances(Long groupId) {
		Map<Long, BigDecimal> rawBalances = calculateBalances(groupId);
		return rawBalances.entrySet().stream().map(entry -> {
			User user = userDao.findById(entry.getKey()).orElse(null);
			String name = (user != null) ? (user.getFullName() != null ? user.getFullName() : user.getUsername())
					: "Unknown User";
			return BalanceDTO.builder().userId(entry.getKey()).userName(name).netBalance(entry.getValue()).build();
		}).collect(Collectors.toList());
	}

	@Transactional
	public SettlementDTO addSettlement(Long groupId, SettlementDTO settlementDTO) {
		Group group = expenseDao.findGroupById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
		User fromUser = userDao.findById(settlementDTO.getFromUserId())
			.orElseThrow(() -> new RuntimeException("Payer not found"));
		User toUser = userDao.findById(settlementDTO.getToUserId())
			.orElseThrow(() -> new RuntimeException("Payee not found"));

		// Validate both users are group members
		List<GroupMember> groupMembers = expenseDao.findMembersByGroup(groupId);
		List<Long> memberIds = groupMembers.stream().map(gm -> gm.getUser().getId()).toList();

		if (!memberIds.contains(settlementDTO.getFromUserId())) {
			throw new RuntimeException("Payer is not a member of this group");
		}
		if (!memberIds.contains(settlementDTO.getToUserId())) {
			throw new RuntimeException("Payee is not a member of this group");
		}

		// Validate amount is positive
		if (settlementDTO.getAmount() == null || settlementDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Settlement amount must be positive");
		}

		Settlement settlement = Settlement.builder()
			.group(group)
			.fromUser(fromUser)
			.toUser(toUser)
			.amount(settlementDTO.getAmount())
			.currency(settlementDTO.getCurrency() != null ? settlementDTO.getCurrency() : "INR")
			.build();

		Settlement saved = expenseDao.saveSettlement(settlement);

		return expenseMapper.toDTO(saved);
	}

}