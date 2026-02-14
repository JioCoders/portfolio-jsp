package com.jiocoders.portfolio.exceptions;

import com.jiocoders.portfolio.models.JioError;
import com.jiocoders.portfolio.models.JioResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<JioResponse<Object>> handleUserException(UserException ex) {
		log.warn("UserException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
			.message(ex.getMessage())
			.status(HttpStatus.BAD_REQUEST.value())
			.errors(List.of(ex.getMessage()))
			.build();

		JioResponse<Object> response = JioResponse.error("User Operation Failed", error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpenseException.class)
	public ResponseEntity<JioResponse<Object>> handleExpenseException(ExpenseException ex) {
		log.warn("ExpenseException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
			.message(ex.getMessage())
			.status(HttpStatus.BAD_REQUEST.value())
			.errors(List.of(ex.getMessage()))
			.build();

		JioResponse<Object> response = JioResponse.error("Expense Operation Failed", error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<JioResponse<Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
		log.warn("Static resource not found: {}", ex.getResourcePath());

		JioError error = JioError.builder()
			.message("Resource not found")
			.status(HttpStatus.NOT_FOUND.value())
			.errors(List.of("The requested resource '" + ex.getResourcePath() + "' was not found"))
			.build();

		JioResponse<Object> response = JioResponse.error("Not Found", error);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<JioResponse<Object>> handleGlobalException(Exception ex) {
		log.error("Unexpected error occurred", ex);

		JioError error = JioError.builder()
			.message("An unexpected error occurred")
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.errors(List.of(ex.getMessage() != null ? ex.getMessage() : "Internal Server Error"))
			.build();

		JioResponse<Object> response = JioResponse.error("System Error", error);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
