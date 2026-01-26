package com.jiocoders.portfolio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "jio.api.prefix=/jiocoders/v1")
@SuppressWarnings("null")
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void login_Success_ShouldReturn200() throws Exception {
		Map<String, String> payload = new HashMap<>();
		payload.put("username", "admin");
		payload.put("password", "pass");

		UserDTO user = new UserDTO();
		user.setUsername("admin");
		when(userService.login(anyString(), anyString())).thenReturn(user);

		mockMvc
			.perform(post("/jiocoders/v1/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(payload)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(1))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.username").value("admin"));
	}

	@Test
	void register_Success_ShouldReturn201() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setUsername("newuser");
		dto.setPassword("pass");

		when(userService.register(any())).thenReturn(dto);

		mockMvc
			.perform(post("/jiocoders/v1/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.status").value(1))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.username").value("newuser"));
	}

}
