package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Data Transfer Object for Expense Split/Distribution")
public class ExpenseSplitDTO {

	@Schema(description = "User ID involved in the split", example = "2")
	private Long userId;

	@Schema(description = "Amount paid by this user", example = "3000.00")
	private BigDecimal paidAmount;

	@Schema(description = "Share amount this user owes", example = "1000.00")
	private BigDecimal shareAmount;

}
