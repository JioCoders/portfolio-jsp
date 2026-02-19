package com.jiocoders.portfolio.exceptions;

import com.jiocoders.portfolio.models.JioError;
import com.jiocoders.portfolio.models.JioResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

	@ExceptionHandler(GroupException.class)
	public ResponseEntity<JioResponse<Object>> handleGroupException(GroupException ex) {
		log.warn("GroupException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(List.of(ex.getMessage()))
				.build();

		JioResponse<Object> response = JioResponse.error("Group Operation Failed", error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GroupException.GroupNotFoundException.class)
	public ResponseEntity<JioResponse<Object>> handleGroupNotFoundException(GroupException.GroupNotFoundException ex) {
		log.warn("GroupNotFoundException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
				.message(ex.getMessage())
				.status(HttpStatus.NOT_FOUND.value())
				.errors(List.of(ex.getMessage()))
				.build();

		JioResponse<Object> response = JioResponse.error("Group Not Found", error);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(GroupException.DuplicateGroupNameException.class)
	public ResponseEntity<JioResponse<Object>> handleDuplicateGroupNameException(
			GroupException.DuplicateGroupNameException ex) {
		log.warn("DuplicateGroupNameException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(List.of(ex.getMessage()))
				.build();

		JioResponse<Object> response = JioResponse.error("Duplicate Group Name", error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GroupException.InvalidGroupOperationException.class)
	public ResponseEntity<JioResponse<Object>> handleInvalidGroupOperationException(
			GroupException.InvalidGroupOperationException ex) {
		log.warn("InvalidGroupOperationException occurred: {}", ex.getMessage());

		JioError error = JioError.builder()
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(List.of(ex.getMessage()))
				.build();

		JioResponse<Object> response = JioResponse.error("Invalid Group Operation", error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<JioResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		log.warn("DataIntegrityViolationException occurred: {}", ex.getMessage());

		String errorMessage = "An unexpected error occurred";
		String userMessage = "Operation failed";

		// Check for unique constraint violation on group name
		if (ex.getMessage() != null && ex.getMessage().contains("PUBLIC.CONSTRAINT_INDEX_7")) {
			errorMessage = "A group with this name already exists";
			userMessage = "Duplicate Group Name";
		}
		// Check for other unique constraint violations
		else if (ex.getMessage() != null && ex.getMessage().contains("unique constraint")
				|| ex.getMessage().contains("Unique index")) {
			errorMessage = "Duplicate entry detected";
			userMessage = "Duplicate Entry";
		}

		JioError error = JioError.builder()
				.message(errorMessage)
				.status(HttpStatus.BAD_REQUEST.value())
				.errors(List.of(errorMessage))
				.build();

		JioResponse<Object> response = JioResponse.error(userMessage, error);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

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
