package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.ExpenseDao;
import com.jiocoders.portfolio.dto.*;
import com.jiocoders.portfolio.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

	private final ExpenseDao expenseDao;

	/**
	 * compute net balance per user in a group Net Balance = (Total Paid) - (Total Share)
	 * + (Settlements Received) - (Settlements Paid)
	 */
	public Map<Long, BigDecimal> calculateBalances(Long groupId) {
		log.info("Calculating balances for group ID: {}", groupId);
		Map<Long, BigDecimal> balances = new HashMap<>();

		// 1. Process Expense Distributions
		List<ExpenseDistribution> distributions = expenseDao.findDistributionsByGroup(groupId);
		for (ExpenseDistribution dist : distributions) {
			Long userId = dist.getUser().getId();
			BigDecimal net = dist.getPaidAmount().subtract(dist.getShareAmount());
			balances.put(userId, balances.getOrDefault(userId, BigDecimal.ZERO).add(net));
		}

		// 2. Adjust for Settlements
		List<Settlement> settlements = expenseDao.findSettlementsByGroup(groupId);
		for (Settlement settle : settlements) {
			Long fromId = settle.getFromUser().getId();
			Long toId = settle.getToUser().getId();
			BigDecimal amount = settle.getAmount();

			// Payer (from) balance increases (they paid their debt)
			balances.put(fromId, balances.getOrDefault(fromId, BigDecimal.ZERO).add(amount));
			// Receiver (to) balance decreases (they received what they were owed)
			balances.put(toId, balances.getOrDefault(toId, BigDecimal.ZERO).subtract(amount));
		}

		log.debug("Calculated balances: {}", balances);
		return balances;
	}

	public GroupDTO createGroup(GroupDTO groupDTO) {
		// Mock implementation - needs real user from context
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public GroupDTO getGroup(Long id) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public ExpenseDTO addExpense(Long groupId, ExpenseDTO expenseDTO) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public List<ExpenseDTO> getExpenses(Long groupId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public List<BalanceDTO> getBalances(Long groupId) {
		Map<Long, BigDecimal> rawBalances = calculateBalances(groupId);
		return rawBalances.entrySet()
			.stream()
			.map(entry -> BalanceDTO.builder()
				.userId(entry.getKey())
				.userName("User " + entry.getKey()) // Placeholder until we fetch names
				.netBalance(entry.getValue())
				.build())
			.collect(Collectors.toList());
	}

	public SettlementDTO addSettlement(Long groupId, SettlementDTO settlementDTO) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
