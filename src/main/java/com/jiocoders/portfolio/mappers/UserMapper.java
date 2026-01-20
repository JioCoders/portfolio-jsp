package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserDTO userDTO);
}
