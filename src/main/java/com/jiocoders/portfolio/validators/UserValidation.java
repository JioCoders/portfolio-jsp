package com.jiocoders.portfolio.validators;

import com.jiocoders.portfolio.exceptions.UserException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public void validateEmail(String email) {
		if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
			throw new UserException("Invalid email format");
		}
	}

	public void validateUserId(Long userId) {
		if (userId == null || userId <= 0) {
			throw new UserException("Invalid user ID");
		}
	}

}
