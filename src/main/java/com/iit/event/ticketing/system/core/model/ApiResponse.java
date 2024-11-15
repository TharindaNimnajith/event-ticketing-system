package com.iit.event.ticketing.system.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Custom API Response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {

  private HttpStatus httpStatus;
  private String message;
  private T data;
  private List<String> errors;

  /**
   * ApiResponse constructor for successful responses without data
   *
   * @param httpStatus HttpStatus
   * @param message    Message
   */
  public ApiResponse(final HttpStatus httpStatus, final String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  /**
   * ApiResponse constructor for successful responses with data
   *
   * @param httpStatus HttpStatus
   * @param message    Message
   * @param data       Data
   */
  public ApiResponse(final HttpStatus httpStatus, final String message, final T data) {
    this.httpStatus = httpStatus;
    this.message = message;
    this.data = data;
  }

  /**
   * ApiResponse constructor for failure responses
   *
   * @param httpStatus HttpStatus
   * @param message    Message
   * @param errors     Errors
   */
  public ApiResponse(final HttpStatus httpStatus, final String message, final List<String> errors) {
    this.httpStatus = httpStatus;
    this.message = message;
    this.errors = errors;
  }
}
