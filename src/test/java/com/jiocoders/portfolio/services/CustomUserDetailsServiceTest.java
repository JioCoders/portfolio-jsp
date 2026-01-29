package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dao.UserDao;
import com.jiocoders.portfolio.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private CustomUserDetailsService userDetailsService;

	@Test
	void loadUserByUsername_Success_ShouldReturnUserDetails() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("pass");
		user.setRole("ADMIN");

		when(userDao.findByUsername(anyString())).thenReturn(Optional.of(user));

		UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

		assertEquals(user.getUsername(), userDetails.getUsername());
		assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
	}

	@Test
    void loadUserByUsername_NotFound_ShouldThrowException() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistent"));
    }

}
