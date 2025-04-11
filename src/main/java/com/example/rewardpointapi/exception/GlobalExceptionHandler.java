package com.example.rewardpointapi.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(NoTransactionDataFoundException.class)
	public ResponseEntity<String> handleNoTransactionDataFoundException(NoTransactionDataFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	/**
	 * Handles IllegalArgumentException thrown when transaction input validation fails,
	 * such as when the transaction amount is null or less than or equal to zero.
	 * Returns a 400 Bad Request response with an appropriate error message.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleInvalidRequest(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	/**
	 * Handles validation errors triggered by @Valid in controller methods.
	 * This catches violations of validation annotations (e.g., @NotNull, @Positive) defined in DTO classes.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + errorMessage);
	}

	/**
	 * Global fallback handler for any uncaught exceptions.
	 * Ensures consistent error response for unexpected server errors.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> error = new HashMap<>();
		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put("error", "Internal Server Error");//500 Internal Server Error.
		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
