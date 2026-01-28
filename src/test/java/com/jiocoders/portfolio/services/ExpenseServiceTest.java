package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.ExpenseDao;
import com.jiocoders.portfolio.dao.UserDao;
import com.jiocoders.portfolio.dto.*;
import com.jiocoders.portfolio.models.*;
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
		// Harry: (+2000 hotel) + (-600 dinner) - (500 settlement received) = +900
		// He was owed 1400. He got 500. Now he is owed 900.
		assertEquals(0, new BigDecimal("900").compareTo(balances.get(harry.getId())), "Harry should have +900");

		// Rahul: (-1000 hotel) + (+1000 dinner) = 0
		assertEquals(0, BigDecimal.ZERO.compareTo(balances.get(rahul.getId())), "Rahul should have 0");

		// Amit: (-1000 hotel) + (-400 dinner) + (500 settlement paid) = -900
		// He owed 1400. He paid 500. Now he owes 900.
		assertEquals(0, new BigDecimal("-900").compareTo(balances.get(amit.getId())), "Amit should have -900");
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
		when(expenseDao.saveGroup(any(Group.class))).thenAnswer(i -> i.getArguments()[0]);

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
		when(expenseDao.saveExpense(any(Expense.class))).thenAnswer(i -> {
			Expense e = (Expense) i.getArguments()[0];
			e.setId(201L);
			if (e.getDistributions() == null)
				e.setDistributions(new ArrayList<>());
			return e;
		});

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
		when(expenseDao.saveSettlement(any(Settlement.class))).thenAnswer(i -> {
			Settlement s = (Settlement) i.getArguments()[0];
			s.setId(501L);
			return s;
		});

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
