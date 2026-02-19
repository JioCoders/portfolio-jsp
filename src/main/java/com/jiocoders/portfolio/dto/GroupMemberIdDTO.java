package com.jiocoders.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Simple DTO for group member ID")
public class GroupMemberIdDTO {

	@JsonProperty("id")
	@Schema(description = "User ID to add as member", example = "1")
	private Long id;

}