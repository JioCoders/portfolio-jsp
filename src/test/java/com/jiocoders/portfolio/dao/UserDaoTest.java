package com.jiocoders.portfolio.dao;

import com.jiocoders.portfolio.entity.User;
import com.jiocoders.portfolio.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDao userDao;

	@Test
    void findByUsername_ShouldCallRepository() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

        Optional<User> result = userDao.findByUsername("test");

        assertTrue(result.isPresent());
        verify(userRepository).findByUsername("test");
    }

	@Test
	void save_ShouldCallRepository() {
		User user = new User();
		userDao.save(user);
		verify(userRepository).save(user);
	}

}
