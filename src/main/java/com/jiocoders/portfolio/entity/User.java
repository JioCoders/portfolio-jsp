package com.jiocoders.portfolio.entity;

import com.jiocoders.portfolio.util.Role;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(name = "name")
	private String fullName;

	@Column(nullable = false)
	private String password;

	private String email;

	private String phone;

	@Enumerated(EnumType.STRING)
	private Role role;

	private boolean isDeleted;

	private LocalDateTime deletedAt;

}
