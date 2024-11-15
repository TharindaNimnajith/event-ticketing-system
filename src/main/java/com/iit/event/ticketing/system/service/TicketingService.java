package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Service
 */
@Service
@Slf4j
public class TicketingService {

  /**
   * Start simulation
   *
   * @return ApiResponse
   */
  public @NonNull ApiResponse<Object> start() {
    log.info("Started simulation");
    return new ApiResponse<>(HttpStatus.OK, "Started simulation");
  }

  /**
   * Stop simulation
   *
   * @return ApiResponse
   */
  public @NonNull ApiResponse<Object> stop() {
    log.info("Stopped simulation");
    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
