package com.jiocoders.portfolio.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Error details for JioResponse")
public class JioError {

	@Schema(description = "General error message", example = "Validation failed")
	private String message;

	@Schema(description = "HTTP Status Code", example = "400")
	private int status;

	@Schema(description = "List of specific error details",
			example = "[\"Username is required\", \"Email is invalid\"]")
	private List<String> errors;

}
