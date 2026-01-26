package com.jiocoders.portfolio.mappers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

	private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

	@Test
	void toDTO_ShouldMapCorrectly() {
		User user = new User();
		user.setId(1L);
		user.setUsername("testuser");
		user.setEmail("test@test.com");
		user.setRole("USER");

		UserDTO dto = mapper.toDTO(user);

		assertEquals(user.getId(), dto.getId());
		assertEquals(user.getUsername(), dto.getUsername());
		assertEquals(user.getEmail(), dto.getEmail());
		assertEquals(user.getRole(), dto.getRole());
	}

	@Test
	void toEntity_ShouldMapCorrectly() {
		UserDTO dto = new UserDTO();
		dto.setUsername("testuser");
		dto.setEmail("test@test.com");
		dto.setPassword("pass123");

		User entity = mapper.toEntity(dto);

		assertNull(entity.getId());
		assertEquals(dto.getUsername(), entity.getUsername());
		assertEquals(dto.getEmail(), entity.getEmail());
		assertEquals(dto.getPassword(), entity.getPassword());
	}

	@Test
	void toDTOs_ShouldMapList() {
		User user = new User();
		user.setUsername("testuser");

		List<UserDTO> dtos = mapper.toDTOs(Collections.singletonList(user));

		assertEquals(1, dtos.size());
		assertEquals(user.getUsername(), dtos.get(0).getUsername());
	}

}
