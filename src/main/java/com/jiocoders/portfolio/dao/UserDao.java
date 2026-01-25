package com.jiocoders.portfolio.dao;

import com.jiocoders.portfolio.models.User;
import com.jiocoders.portfolio.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDao {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        log.debug("DAO: Fetching user by username: {}", username);
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("DAO: Error fetching user by username: {}", username, e);
            throw e;
        }
    }

    public Optional<User> findByEmail(String email) {
        log.debug("DAO: Fetching user by email: {}", email);
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("DAO: Error fetching user by email: {}", email, e);
            throw e;
        }
    }

    public User save(User user) {
        log.debug("DAO: Saving user: {}", user.getUsername());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("DAO: Error saving user: {}", user.getUsername(), e);
            throw e;
        }
    }

    public List<User> findAll() {
        log.debug("DAO: Fetching all users");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("DAO: Error fetching all users", e);
            throw e;
        }
    }
}
