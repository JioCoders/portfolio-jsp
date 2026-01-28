package com.jiocoders.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/h2-console/**", "/", "/portfolio", "/login", "/css/**", "/js/**", "/images/**",
						"/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/sw.js", "/robots.txt",
						"/favicon.ico")
				.permitAll()
				.anyRequest()
				.authenticated())
			.headers(headers -> headers.frameOptions(frame -> frame.disable()))
			.httpBasic(basic -> {
			}); // Enable Basic Auth for testing protected endpoints

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
