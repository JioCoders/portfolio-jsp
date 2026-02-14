package com.jiocoders.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "expense_distributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDistribution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id", nullable = false)
	private Expense expense;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "paid_amount", precision = 19, scale = 4)
	@Builder.Default
	private BigDecimal paidAmount = BigDecimal.ZERO;

	@Column(name = "share_amount", precision = 19, scale = 4)
	@Builder.Default
	private BigDecimal shareAmount = BigDecimal.ZERO;

}
