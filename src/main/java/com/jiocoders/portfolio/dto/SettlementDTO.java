package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Data Transfer Object for Settlement")
public class SettlementDTO {

	@Schema(description = "Settlement ID", example = "501")
	private Long id;

	@Schema(description = "User ID who pays", example = "3")
	private Long fromUserId;

	@Schema(description = "User ID who receives", example = "1")
	private Long toUserId;

	@Schema(description = "Amount settled", example = "500.00")
	private BigDecimal amount;

	@Schema(description = "Currency", example = "INR")
	@Builder.Default
	private String currency = "INR";

	@Schema(description = "Timestamp of settlement")
	private LocalDateTime settledAt;

}
