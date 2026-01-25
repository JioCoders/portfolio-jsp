package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for User information")
public class UserDTO {

	@Schema(description = "Unique identifier of the user", example = "1")
	private Long id;

	@Schema(description = "Username of the user", example = "jiocoders")
	private String username;

	@Schema(description = "Email address of the user", example = "team@jiocoders.com")
	private String email;

	@Schema(description = "Role of the user", example = "ROLE_ADMIN")
	private String role;

	@Schema(description = "Password of the user (used for registration only)", example = "password123",
			accessMode = Schema.AccessMode.WRITE_ONLY)
	private String password; // For registration

}
