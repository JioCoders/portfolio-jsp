package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.GroupDTO;
import com.jiocoders.portfolio.entity.Group;
import com.jiocoders.portfolio.entity.GroupMember;
import com.jiocoders.portfolio.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public abstract class GroupMapper {

	@Autowired
	protected UserMapper userMapper;

	@Mapping(target = "createdBy", source = "creator.id")
	@Mapping(target = "members", source = "members", qualifiedByName = "mapMembers")
	public abstract GroupDTO toDTO(Group group);

	public abstract List<GroupDTO> toDTOs(List<Group> groups);

	@Named("mapMembers")
	protected List<UserDTO> mapMembers(List<GroupMember> members) {
		if (members == null)
			return null;
		return members.stream().map(member -> userMapper.toDTO(member.getUser())).collect(Collectors.toList());
	}

}
