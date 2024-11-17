package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Service
 */
@Service
public class TicketingService {

  /**
   * Start simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> start() {
    return new ApiResponse<>(HttpStatus.OK, "Started simulation");
  }

  /**
   * Stop simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> stop() {
    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
