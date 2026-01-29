package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.mappers.UserMapper;
import com.jiocoders.portfolio.entity.User;
import com.jiocoders.portfolio.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserDao userDao;

	private final UserMapper userMapper;

	private final com.jiocoders.portfolio.validators.UserValidation userValidation;

	private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

	public UserDTO login(String username, String password) {
		log.debug("Authenticating user: {}", username);
		Optional<User> userOpt = userDao.findByUsername(username);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				log.debug("Password match for user: {}", username);
				return userMapper.toDTO(user);
			}
			else {
				log.warn("Password mismatch for user: {}", username);
			}
		}
		else {
			log.warn("User not found: {}", username);
		}
		return null;
	}

	@Transactional
	public UserDTO register(UserDTO userDTO) {
		log.info("Registering new user: {}", userDTO.getUsername());
		userValidation.validateEmail(userDTO.getEmail());

		if (userDao.findByUsername(userDTO.getUsername()).isPresent()) {
			log.warn("Registration failed: User already exists - {}", userDTO.getUsername());
			throw new com.jiocoders.portfolio.exceptions.UserException("User already exists");
		}
		User user = userMapper.toEntity(userDTO);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Default role for now
		if (user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("USER");
		}
		user = userDao.save(user);
		log.info("User registered successfully. ID: {}", user.getId());
		return userMapper.toDTO(user);
	}

	public java.util.List<UserDTO> getAllUsers() {
		log.info("Fetching all users");
		return userMapper.toDTOs(userDao.findAll());
	}

	public UserDTO getUserByUsernameOrEmail(String identifier) {
		log.info("Searching for user with identifier: {}", identifier);
		Optional<User> user = userDao.findByUsername(identifier);
		if (user.isEmpty()) {
			user = userDao.findByEmail(identifier);
		}
		return user.map(userMapper::toDTO).orElseThrow(() -> {
			log.error("User not found: {}", identifier);
			return new com.jiocoders.portfolio.exceptions.UserException(
					"User not found with identifier: " + identifier);
		});
	}

}
