package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Data Transfer Object for User Balance in a Group")
public class BalanceDTO {

	@Schema(description = "User ID", example = "1")
	private Long userId;

	@Schema(description = "User's full name", example = "Harry Potter")
	private String userName;

	@Schema(description = "Net balance (Positive = Owed, Negative = Owes)", example = "2000.00")
	private BigDecimal netBalance;

}
