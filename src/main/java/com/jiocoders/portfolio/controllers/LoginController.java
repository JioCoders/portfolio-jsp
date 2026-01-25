package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.LoginRequest;
import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoints for user login and registration")
public class LoginController {

	private final UserService userService;

	@PostMapping("/login")
	@Operation(summary = "Login a user", description = "Authenticates a user and returns their profile information.")
	@ApiResponse(responseCode = "200", description = "Login successful",
			content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "401", description = "Invalid credentials")
	@ApiResponse(responseCode = "400", description = "Invalid request payload")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		log.info("Login attempt for user: {}", username);

		if (username == null || loginRequest.getPassword() == null) {
			log.warn("Login failed: missing username or password");
			return ResponseEntity.badRequest().body("Username and password are required");
		}

		UserDTO user = userService.login(username, loginRequest.getPassword());
		if (user != null) {
			log.info("Login successful for user: {}", username);
			return ResponseEntity.ok(user);
		}
		else {
			log.warn("Login failed: invalid credentials for user: {}", username);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}

	@PostMapping("/register")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Register a new user", description = "Creates a new user account. Requires ADMIN role.")
	@ApiResponse(responseCode = "201", description = "User created successfully",
			content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "400", description = "Registration failed")
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
		log.info("Registration attempt for username: {}", userDTO.getUsername());
		try {
			UserDTO createdUser = userService.register(userDTO);
			log.info("Registration successful for username: {}", createdUser.getUsername());
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		}
		catch (RuntimeException e) {
			log.error("Registration failed for username: {}. Reason: {}", userDTO.getUsername(), e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
