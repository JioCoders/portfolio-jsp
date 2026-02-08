package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.UserDao;
import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.dto.UserRegisterDTO;
import com.jiocoders.portfolio.exceptions.UserException;
import com.jiocoders.portfolio.mappers.UserMapper;
import com.jiocoders.portfolio.entity.User;
import com.jiocoders.portfolio.validators.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserDao userDao;

	@Mock
	private UserMapper userMapper;

	@Mock
	private UserValidation userValidation;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	private UserDTO userDTO;

	private UserRegisterDTO userRegisterDTO;

	private User user;

	@BeforeEach
	void setUp() {
		userRegisterDTO = new UserRegisterDTO();
		userDTO.setEmail("test@test.com");
		userRegisterDTO.setUsername("testuser");
		userRegisterDTO.setPassword("rawPass");
		userDTO = new UserDTO();
		userDTO.setUsername("testuser");
		userDTO.setEmail("test@test.com");

		user = new User();
		user.setUsername("testuser");
		user.setPassword("encodedPass");
	}

	@Test
    void register_Success_ShouldReturnDTO() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userMapper.toUserEntity(any())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(userDao.save(any())).thenReturn(user);
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        UserDTO result = userService.register(userRegisterDTO);

        assertNotNull(result);
        verify(userValidation).validateEmail(userDTO.getEmail());
        verify(userDao).save(any(User.class));
    }

	@Test
    void register_UserExists_ShouldThrowException() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserException.class, () -> userService.register(userRegisterDTO));
        verify(userDao, never()).save(any());
    }

	@Test
    void login_Success_ShouldReturnDTO() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        UserDTO result = userService.login("testuser", "rawPass");

        assertNotNull(result);
    }

	@Test
    void login_Failure_ShouldReturnNull() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        UserDTO result = userService.login("testuser", "wrongPass");

        assertNull(result);
    }

}
