package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.ExpenseDao;
import com.jiocoders.portfolio.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

	@Mock
	private ExpenseDao expenseDao;

	@InjectMocks
	private ExpenseService expenseService;

	private User harry;

	private User rahul;

	private User amit;

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
	}

	@Test
	void testGoaTripScenario() {
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

	private ExpenseDistribution createDist(User user, double paid, double share) {
		return ExpenseDistribution.builder()
			.user(user)
			.paidAmount(new BigDecimal(paid))
			.shareAmount(new BigDecimal(share))
			.build();
	}

}
