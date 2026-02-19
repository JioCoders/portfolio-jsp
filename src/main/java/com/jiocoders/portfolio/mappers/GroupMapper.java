package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.GroupDTO;
import com.jiocoders.portfolio.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

	@Mapping(target = "createdBy", source = "creator.id")
	@Mapping(target = "members", ignore = true) // Handled manually
	GroupDTO toDTO(Group group);

	@Mapping(target = "creator", ignore = true)
	@Mapping(target = "deleted", ignore = true)
	@Mapping(target = "expenses", ignore = true)
	@Mapping(target = "members", ignore = true)
	Group toEntity(GroupDTO groupDTO);

	List<GroupDTO> toDTOs(List<Group> groups);

}