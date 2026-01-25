package com.jiocoders.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Wrapper DTO for a list of users")
public class UserListDTO {

	@Schema(description = "The list of users")
	private List<UserDTO> users;

}
