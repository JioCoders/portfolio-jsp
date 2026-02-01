package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Data Transfer Object for Group information")
public class GroupDTO {

	@Schema(description = "Unique identifier of the group", example = "101")
	private Long id;

	@Schema(description = "Name of the group", example = "Goa Trip")
	private String name;

	@Schema(description = "Description of the group", example = "Summer vacation 2024")
	private String description;

	@Schema(description = "ID of the user who created the group", example = "1")
	private Long createdBy;

	@Schema(description = "List of members in the group")
	private List<UserDTO> members;

}
