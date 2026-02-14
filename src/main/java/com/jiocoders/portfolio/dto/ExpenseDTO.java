package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Schema(description = "Data Transfer Object for Expense information")
public class ExpenseDTO {

	@Schema(description = "Unique identifier of the expense", example = "201")
	private Long id;

	@Schema(description = "Title of the expense", example = "Hotel Booking")
	private String title;

	@Schema(description = "Description", example = "Taj Hotel for 2 nights")
	private String description;

	@Schema(description = "Total amount of the expense", example = "3000.00")
	private BigDecimal totalAmount;

	@Schema(description = "Currency code", example = "INR")
	@Builder.Default
	private String currency = "INR";

	@Schema(description = "Date the expense was incurred", example = "2024-05-15")
	private LocalDate expenseDate;

	@Schema(description = "ID of the group this expense belongs to")
	private Long groupId;

	@Schema(description = "List of how the expense is split")
	private List<ExpenseSplitDTO> splits;

}
