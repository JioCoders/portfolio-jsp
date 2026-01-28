package com.jiocoders.portfolio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@Column(name = "entity_type", nullable = false, length = 50)
	private String entityType;

	@Column(name = "entity_id", nullable = false)
	private Long entityId;

	@Column(nullable = false, length = 50)
	private String action;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performed_by", nullable = false)
	private User performedBy;

	@Column(columnDefinition = "TEXT")
	private String details;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

}
