package com.jiocoders.portfolio.controllers;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
    void getAllUsers_ShouldReturn200() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(1))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.users").isArray());
    }

	@Test
	void getUser_ShouldReturn200() throws Exception {
		UserDTO user = new UserDTO();
		user.setUsername("admin");
		when(userService.getUserByUsernameOrEmail("admin")).thenReturn(user);

		mockMvc.perform(get("/admin/user/admin"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(1))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.username").value("admin"));
	}

}
