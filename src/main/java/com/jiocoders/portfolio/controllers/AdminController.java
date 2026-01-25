package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
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
public class AdminController {

	private final UserService userService;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		log.info("Admin request: fetching all users");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/user/{identifier}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> getUser(@PathVariable String identifier) {
		log.info("Admin request: searching for user '{}'", identifier);
		return ResponseEntity.ok(userService.getUserByUsernameOrEmail(identifier));
	}

}
