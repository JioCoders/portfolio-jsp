package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO toDTO(User user);

	List<UserDTO> toDTOs(List<User> users);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	User toEntity(UserDTO userDTO);

}
