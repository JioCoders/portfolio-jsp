package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.GroupMemberDTO;
import com.jiocoders.portfolio.entity.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface GroupMemberMapper {

	@Mapping(source = "group.id", target = "groupId")
	GroupMemberDTO toDTO(GroupMember groupMember);

	List<GroupMemberDTO> toDTOs(List<GroupMember> groupMembers);

}