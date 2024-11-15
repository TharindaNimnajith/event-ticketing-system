package com.iit.event.ticketing.system.exception;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * API Exception Handler
 */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

  /**
   * Handles validation exceptions that occur when request input fails validation checks
   *
   * @param ex MethodArgumentNotValidException
   * @return ResponseEntity containing an ApiResponse with a 400 status code, an error message, and a list of validation error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(final MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);

    // Extract field-specific error messages
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .toList();

    // Create a standardized response
    ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Validation error", errors);

    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Handles all other exceptions that are not explicitly handled elsewhere
   *
   * @param ex Exception
   * @return ResponseEntity containing an ApiResponse with a 500 status code, an error message, and the exception details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleGeneralExceptions(final Exception ex) {
    log.error(ex.getMessage(), ex);

    // Create a standardized response
    ApiResponse<Object> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", List.of(ex.getMessage()));

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
