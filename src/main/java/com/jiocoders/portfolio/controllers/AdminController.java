package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Admin", description = "Administrative endpoints for user management")
public class AdminController {

	private final UserService userService;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all users",
			description = "Retrieves a list of all registered users. Requires ADMIN role.")
	@ApiResponse(responseCode = "200", description = "Successful retrieval",
			content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		log.info("Admin request: fetching all users");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/user/{identifier}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get a specific user",
			description = "Retrieves detailed information for a user by their username or email. Requires ADMIN role.")
	@ApiResponse(responseCode = "200", description = "User found",
			content = @Content(schema = @Schema(implementation = UserDTO.class)))
	@ApiResponse(responseCode = "404", description = "User not found")
	public ResponseEntity<UserDTO> getUser(@PathVariable String identifier) {
		log.info("Admin request: searching for user '{}'", identifier);
		return ResponseEntity.ok(userService.getUserByUsernameOrEmail(identifier));
	}

}
