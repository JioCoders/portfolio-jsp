package com.jiocoders.portfolio.validators;

import com.jiocoders.portfolio.exceptions.UserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

	private final UserValidation validator = new UserValidation();

	@Test
	void validateEmail_ValidEmail_ShouldNotThrowException() {
		assertDoesNotThrow(() -> validator.validateEmail("test@example.com"));
	}

	@Test
	void validateEmail_InvalidEmail_ShouldThrowException() {
		UserException exception = assertThrows(UserException.class, () -> validator.validateEmail("invalid-email"));
		assertEquals("Invalid email format", exception.getMessage());
	}

	@Test
	void validateEmail_NullEmail_ShouldThrowException() {
		assertThrows(UserException.class, () -> validator.validateEmail(null));
	}

	@Test
	void validateUserId_ValidId_ShouldNotThrowException() {
		assertDoesNotThrow(() -> validator.validateUserId(1L));
	}

	@Test
	void validateUserId_InvalidId_ShouldThrowException() {
		assertThrows(UserException.class, () -> validator.validateUserId(0L));
		assertThrows(UserException.class, () -> validator.validateUserId(-1L));
	}

	@Test
	void validateUserId_NullId_ShouldThrowException() {
		assertThrows(UserException.class, () -> validator.validateUserId(null));
	}

}
