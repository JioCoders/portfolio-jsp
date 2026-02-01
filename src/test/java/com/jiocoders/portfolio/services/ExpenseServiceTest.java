package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.ExpenseDao;
import com.jiocoders.portfolio.dao.UserDao;
import com.jiocoders.portfolio.dto.*;
import com.jiocoders.portfolio.entity.*;
import com.jiocoders.portfolio.mappers.ExpenseMapper;
import com.jiocoders.portfolio.mappers.GroupMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

	@Mock
	private ExpenseDao expenseDao;

	@Mock
	private UserDao userDao;

	@Mock
	private GroupMapper groupMapper;

	@Mock
	private ExpenseMapper expenseMapper;

	@InjectMocks
	private ExpenseService expenseService;

	private User harry;

	private User rahul;

	private User amit;

	private Group group;

	@BeforeEach
	void setUp() {
		harry = new User();
		harry.setId(1L);
		harry.setUsername("harry");
		rahul = new User();
		rahul.setId(2L);
		rahul.setUsername("rahul");
		amit = new User();
		amit.setId(3L);
		amit.setUsername("amit");

		group = Group.builder().id(101L).name("Goa Trip").creator(harry).build();
	}

	@Test
	void testGoaTripCalculation() {
		// --- SCENARIO STORY ---
		// Harry, Rahul, and Amit are on a trip to Goa.
		// 1. HOTEL: Harry pays 3000 for the hotel. Everyone splits it equally (1000
		// each).
		// Impact: Harry is owed +2000. Rahul owes -1000. Amit owes -1000.
		// 2. DINNER: Rahul pays 1500 for dinner. Unequal split: Harry owes 600, Rahul
		// 500, Amit 400.
		// Impact: Rahul is owed +1000 (1500 paid - 500 share). Harry owes -600. Amit
		// owes -400.
		// 3. SETTLEMENT: Amit pays Harry 500 to reduce his debt.
		// Impact: Amit's debt reduces by 500. Harry's expected receipt reduces by 500
		// (since he got cash).

		Long groupId = 101L;

		// --- Mock Expense 1: Hotel (3000) ---
		// Harry pays 3000. Split equally (1000 each).
		List<ExpenseDistribution> hotelSplits = new ArrayList<>();
		hotelSplits.add(createDist(harry, 3000, 1000));
		hotelSplits.add(createDist(rahul, 0, 1000));
		hotelSplits.add(createDist(amit, 0, 1000));

		// --- Mock Expense 2: Dinner (1500) ---
		// Rahul pays 1500. Harry owes 600, Rahul 500, Amit 400.
		List<ExpenseDistribution> dinnerSplits = new ArrayList<>();
		dinnerSplits.add(createDist(harry, 0, 600));
		dinnerSplits.add(createDist(rahul, 1500, 500));
		dinnerSplits.add(createDist(amit, 0, 400));

		// Combine all distributions mock
		List<ExpenseDistribution> allDistributions = new ArrayList<>();
		allDistributions.addAll(hotelSplits);
		allDistributions.addAll(dinnerSplits);

		when(expenseDao.findDistributionsByGroup(groupId)).thenReturn(allDistributions);

		// --- Mock Settlement ---
		// Amit pays Harry 500
		List<Settlement> settlements = new ArrayList<>();
		Settlement settlement = Settlement.builder().fromUser(amit).toUser(harry).amount(new BigDecimal("500")).build();
		settlements.add(settlement);

		when(expenseDao.findSettlementsByGroup(groupId)).thenReturn(settlements);

		// --- Execute ---
		Map<Long, BigDecimal> balances = expenseService.calculateBalances(groupId);

		// --- Verify ---
		// With corrected logic: Share - Paid + Settlements
		// Harry expenses: Hotel(1000-3000=-2000) + Dinner(600-0=+600) = -1400
		// Harry settlement: Receives 500 = -1400 + 500 = -900
		// Rahul expenses: Hotel(1000-0=+1000) + Dinner(500-1500=-1000) = 0
		// Amit expenses: Hotel(1000-0=+1000) + Dinner(400-0=+400) = +1400
		// Amit settlement: Pays 500 = +1400 - 500 = +900

		assertEquals(0, new BigDecimal("-900").compareTo(balances.get(harry.getId())), "Harry should have -900");
		assertEquals(0, BigDecimal.ZERO.compareTo(balances.get(rahul.getId())), "Rahul should have 0");
		assertEquals(0, new BigDecimal("900").compareTo(balances.get(amit.getId())), "Amit should have +900");
	}

	@Test
	void testCreateGroup() {
		// --- STORY: STARTING THE TRIP ---
		// Harry decides to start a group called "Goa Trip" and adds his friend Rahul
		// immediately.

		GroupDTO inputDTO = GroupDTO.builder()
				.name("Goa Trip")
				.description("Fun")
				.createdBy(1L)
				.members(List.of(UserDTO.builder().id(2L).build()))
				.build();

		when(userDao.findById(1L)).thenReturn(Optional.of(harry));
		when(userDao.findById(2L)).thenReturn(Optional.of(rahul));
		when(expenseDao.saveGroup(any(Group.class))).thenAnswer(i -> {
			Group g = (Group) i.getArguments()[0];
			g.setMembers(new ArrayList<>()); // initialize members for mapper
			return g;
		});

		// Stub mapper
		GroupDTO expectedGroupDTO = GroupDTO.builder().name("Goa Trip").build();
		when(groupMapper.toDTO(any(Group.class))).thenReturn(expectedGroupDTO);

		GroupDTO result = expenseService.createGroup(inputDTO);

		assertNotNull(result);
		assertEquals("Goa Trip", result.getName());
		verify(expenseDao, times(2)).addMemberToGroup(any(GroupMember.class)); // 1 admin
																				// + 1
																				// member
	}

	@Test
	void testAddExpense() {
		// --- STORY: PAYING FOR THE HOTEL ---
		// The group arrives. Harry swipes his card for the Hotel (3000 INR).
		// He logs it in the app, splitting it purely equally (1000 each) among Harry,
		// Rahul, and Amit.

		ExpenseDTO inputDTO = ExpenseDTO.builder()
				.title("Hotel")
				.totalAmount(new BigDecimal("3000"))
				.expenseDate(LocalDate.now())
				.splits(List.of(
						ExpenseSplitDTO.builder()
								.userId(1L)
								.paidAmount(new BigDecimal("3000"))
								.shareAmount(new BigDecimal("1000"))
								.build(),
						ExpenseSplitDTO.builder()
								.userId(2L)
								.paidAmount(BigDecimal.ZERO)
								.shareAmount(new BigDecimal("1000"))
								.build(),
						ExpenseSplitDTO.builder()
								.userId(3L)
								.paidAmount(BigDecimal.ZERO)
								.shareAmount(new BigDecimal("1000"))
								.build()))
				.build();

		when(expenseDao.findGroupById(101L)).thenReturn(Optional.of(group));
		when(userDao.findById(1L)).thenReturn(Optional.of(harry));
		when(userDao.findById(2L)).thenReturn(Optional.of(rahul));
		when(userDao.findById(3L)).thenReturn(Optional.of(amit));

		// Mock group members
		List<GroupMember> mockMembers = List.of(GroupMember.builder().user(harry).build(),
				GroupMember.builder().user(rahul).build(), GroupMember.builder().user(amit).build());
		when(expenseDao.findMembersByGroup(101L)).thenReturn(mockMembers);
		when(expenseDao.saveExpense(any(Expense.class))).thenAnswer(i -> {
			Expense e = (Expense) i.getArguments()[0];
			e.setId(201L);
			if (e.getDistributions() == null)
				e.setDistributions(new ArrayList<>());
			return e;
		});

		// Stub mapper
		ExpenseDTO expectedDTO = ExpenseDTO.builder().id(201L).title("Hotel").build();
		when(expenseMapper.toDTO(any(Expense.class))).thenReturn(expectedDTO);

		ExpenseDTO result = expenseService.addExpense(101L, inputDTO);

		assertNotNull(result);
		assertEquals(201L, result.getId());
		verify(expenseDao, atLeastOnce()).saveExpense(any(Expense.class));
	}

	@Test
	void testAddSettlement() {
		// --- STORY: SETTLING DEBTS ---
		// Amit checks the app and sees he owes a lot. He transfers 500 INR to Harry via
		// UPI.
		// He records this settlement in the app so his balance updates.

		SettlementDTO inputDTO = SettlementDTO.builder()
				.fromUserId(3L)
				.toUserId(1L)
				.amount(new BigDecimal("500"))
				.build();

		when(expenseDao.findGroupById(101L)).thenReturn(Optional.of(group));
		when(userDao.findById(3L)).thenReturn(Optional.of(amit));
		when(userDao.findById(1L)).thenReturn(Optional.of(harry));

		// Mock group members
		List<GroupMember> mockMembers = List.of(GroupMember.builder().user(amit).build(),
				GroupMember.builder().user(harry).build());
		when(expenseDao.findMembersByGroup(101L)).thenReturn(mockMembers);
		when(expenseDao.saveSettlement(any(Settlement.class))).thenAnswer(i -> {
			Settlement s = (Settlement) i.getArguments()[0];
			s.setId(501L);
			return s;
		});

		// Stub mapper
		SettlementDTO expectedDTO = SettlementDTO.builder().id(501L).amount(new BigDecimal("500")).build();
		when(expenseMapper.toDTO(any(Settlement.class))).thenReturn(expectedDTO);

		SettlementDTO result = expenseService.addSettlement(101L, inputDTO);

		assertEquals(501L, result.getId());
		assertEquals(new BigDecimal("500"), result.getAmount());
	}

	private ExpenseDistribution createDist(User user, double paid, double share) {
		return ExpenseDistribution.builder()
				.user(user)
				.paidAmount(new BigDecimal(paid))
				.shareAmount(new BigDecimal(share))
				.build();
	}

}
