package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Generic message response")
public class MessageResponse {

	@Schema(description = "The message content", example = "Hi, I'm Jiocoders team")
	private String message;

}
