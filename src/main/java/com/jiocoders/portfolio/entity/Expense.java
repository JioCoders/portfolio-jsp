package com.jiocoders.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@Column(nullable = false)
	private String title;

	private String description;

	@Column(name = "total_amount", nullable = false, precision = 19, scale = 4)
	private BigDecimal totalAmount;

	@Column(length = 10)
	@Builder.Default
	private String currency = "INR";

	@Column(name = "expense_date", nullable = false)
	private LocalDate expenseDate;

	@Column(name = "is_deleted")
	@Builder.Default
	private boolean deleted = false;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<ExpenseDistribution> distributions = new ArrayList<>();

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

}
