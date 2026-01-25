package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        log.info("Login attempt for user: {}", username);

        if (username == null || payload.get("password") == null) {
            log.warn("Login failed: missing username or password");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        UserDTO user = userService.login(username, payload.get("password"));
        if (user != null) {
            log.info("Login successful for user: {}", username);
            return ResponseEntity.ok(user);
        } else {
            log.warn("Login failed: invalid credentials for user: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        log.info("Registration attempt for username: {}", userDTO.getUsername());
        try {
            UserDTO createdUser = userService.register(userDTO);
            log.info("Registration successful for username: {}", createdUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            log.error("Registration failed for username: {}. Reason: {}", userDTO.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
