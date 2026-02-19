package com.jiocoders.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiocoders.portfolio.util.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Group Member information")
public class GroupMemberDTO {

	@Schema(description = "Unique identifier of the group member", example = "1")
	private Long id;

	@Schema(description = "ID of the group", example = "101")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Long groupId;

	@Schema(description = "User information")
	private UserDTO user;

	@Schema(description = "Role of the user in the group", example = "MEMBER")
	private Role role;

}