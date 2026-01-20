package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.dto.UserDTO;
import com.jiocoders.portfolio.mappers.UserMapper;
import com.jiocoders.portfolio.models.User;
import com.jiocoders.portfolio.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // In a real app, use BCrypt!
            if (user.getPassword().equals(password)) {
                return userMapper.toDTO(user);
            }
        }
        return null;
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = userMapper.toEntity(userDTO);
        // Default role for now
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
