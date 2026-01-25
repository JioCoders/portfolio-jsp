package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "General API", description = "Test and demonstration endpoints")
public class ApiController {

	// Serve JSON response
	// GET API: /api/hello
	@GetMapping("/api/hello")
	@Operation(summary = "Get a greeting message", description = "Returns a simple greeting from the Jiocoders team.")
	@ApiResponse(responseCode = "200", description = "Successful greeting",
			content = @Content(schema = @Schema(implementation = MessageResponse.class)))
	public MessageResponse helloApi() {
		return new MessageResponse("Hi, I'm Jiocoders team");
	}

	// POST API: /api/hello
	@PostMapping("/api/hello")
	@Operation(summary = "Get a personalized greeting",
			description = "Takes a name and returns a personalized greeting.")
	@ApiResponse(responseCode = "200", description = "Successful greeting",
			content = @Content(schema = @Schema(implementation = MessageResponse.class)))
	public MessageResponse helloPost(@RequestBody Map<String, String> body) {
		String name = body.getOrDefault("name", "friend");
		return new MessageResponse("Hi, I'm " + name + " from Jiocoders team");
	}

}