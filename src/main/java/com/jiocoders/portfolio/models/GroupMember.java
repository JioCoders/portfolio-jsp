package com.jiocoders.portfolio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_members", uniqueConstraints = { @UniqueConstraint(columnNames = { "group_id", "user_id" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(length = 50)
	@Builder.Default
	private String role = "MEMBER";

	@Column(name = "joined_at")
	private LocalDateTime joinedAt;

	@PrePersist
	protected void onJoin() {
		joinedAt = LocalDateTime.now();
	}

}
