package com.jiocoders.portfolio.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user login")
public class LoginRequest {

	@Schema(description = "Username for login", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;

	@Schema(description = "Password for login", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
	private String password;

}
