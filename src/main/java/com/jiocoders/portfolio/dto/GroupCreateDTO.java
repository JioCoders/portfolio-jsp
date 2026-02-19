package com.jiocoders.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Group creation")
public class GroupCreateDTO {

	@Schema(description = "Name of the group", example = "Goa Trip")
	private String name;

	@Schema(description = "Description of the group", example = "Summer vacation 2024")
	private String description;

	@Schema(description = "ID of the user who created the group", example = "1")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Long createdBy;

	@Schema(description = "List of user IDs to add as members")
	@JsonProperty("members")
	private List<MemberIdDTO> members;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberIdDTO {

		@JsonProperty("id")
		@Schema(description = "User ID to add as member", example = "1")
		private Long id;

	}

}