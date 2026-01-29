package com.jiocoders.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settlement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id", nullable = false)
	private User fromUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id", nullable = false)
	private User toUser;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal amount;

	@Column(length = 10)
	private String currency = "INR";

	@Column(name = "settled_at")
	private LocalDateTime settledAt;

	@PrePersist
	protected void onSettle() {
		settledAt = LocalDateTime.now();
	}

}
