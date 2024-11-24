package com.iit.event.ticketing.system.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * Custom API Response
 */
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApiResponse<T> {

  @JsonProperty("http_status")
  @NonNull
  private HttpStatus httpStatus;

  @JsonProperty("message")
  @NonNull
  private String message;

  @JsonProperty("data")
  private T data;

  @JsonProperty("errors")
  private List<String> errors;

  /**
   * ApiResponse constructor for successful responses without data
   *
   * @param httpStatus HttpStatus (Not null)
   * @param message    Message (Not null)
   */
  public ApiResponse(final @NonNull HttpStatus httpStatus, final @NonNull String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  /**
   * ApiResponse constructor for successful responses with data
   *
   * @param httpStatus HttpStatus (Not null)
   * @param message    Message (Not null)
   * @param data       Data (Not null)
   */
  public ApiResponse(final @NonNull HttpStatus httpStatus, final @NonNull String message, final @NonNull T data) {
    this.httpStatus = httpStatus;
    this.message = message;
    this.data = data;
  }

  /**
   * ApiResponse constructor for failure responses
   *
   * @param httpStatus HttpStatus (Not null)
   * @param message    Message (Not null)
   * @param errors     Errors (Not null)
   */
  public ApiResponse(final @NonNull HttpStatus httpStatus, final @NonNull String message, final @NonNull List<String> errors) {
    this.httpStatus = httpStatus;
    this.message = message;
    this.errors = errors;
  }
}
