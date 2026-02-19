package com.jiocoders.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiocoders.portfolio.util.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for User information")
public class UserDTO {

	@Schema(description = "Unique identifier of the user", example = "1")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Long id;

	@Schema(description = "Username of the user", example = "jiocoders")
	private String username;

	@Schema(description = "Full name of the user", example = "Harry Potter")
	private String fullName;

	@Schema(description = "Email address of the user", example = "team@jiocoders.com")
	private String email;

	@Schema(description = "Phone number of the user", example = "+919876543210")
	private String phone;

	@Schema(description = "Role of the user", example = "ADMIN")
	private Role role;

}
