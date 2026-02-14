package com.jiocoders.portfolio.services;

import com.jiocoders.portfolio.entity.User;
import com.jiocoders.portfolio.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Loading user details for: {}", username);
		User user = userDao.findByUsername(username).orElseThrow(() -> {
			log.error("User not found during authentication: {}", username);
			return new UsernameNotFoundException("User not found: " + username);
		});

		// Ensure role has ROLE_ prefix if not present
		String roleName = user.getRole();
		if (!roleName.startsWith("ROLE_")) {
			roleName = "ROLE_" + roleName;
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(roleName)));
	}

}
