package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.ExpenseDTO;
import com.jiocoders.portfolio.dto.ExpenseSplitDTO;
import com.jiocoders.portfolio.dto.SettlementDTO;
import com.jiocoders.portfolio.entity.Expense;
import com.jiocoders.portfolio.entity.ExpenseDistribution;
import com.jiocoders.portfolio.entity.Settlement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

	@Mapping(target = "groupId", source = "group.id")
	@Mapping(target = "splits", source = "distributions")
	ExpenseDTO toDTO(Expense expense);

	List<ExpenseDTO> toDTOs(List<Expense> expenses);

	@Mapping(target = "userId", source = "user.id")
	ExpenseSplitDTO toSplitDTO(ExpenseDistribution distribution);

	@Mapping(target = "fromUserId", source = "fromUser.id")
	@Mapping(target = "toUserId", source = "toUser.id")
	SettlementDTO toDTO(Settlement settlement);

	// BalanceDTO is manually created from a Map usually, but we can add a helper if
	// needed.
	// expenseService.getBalances returns List<BalanceDTO> from Map<Long,
	// BigDecimal>.
	// This logic involves fetching User names which is service level logic
	// (UserDao).
	// So we'll leave balance creation in Service or move it to a specific method if
	// needed.

}
