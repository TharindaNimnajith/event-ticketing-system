package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.service.TicketingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticketing Controller
 */
@RestController
@RequestMapping("v1/ticketing")
@RequiredArgsConstructor
@Slf4j
public class TicketingController {

  @NonNull
  private final TicketingService ticketingService;

  /**
   * Start simulation
   *
   * @return ResponseEntity containing ApiResponse (Not null)
   */
  @PostMapping("start")
  public @NonNull ResponseEntity<ApiResponse<Object>> startSimulation() {
    log.info("Start simulation");
    ApiResponse<Object> apiResponse = ticketingService.startSimulation();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

  /**
   * Stop simulation
   *
   * @return ResponseEntity containing ApiResponse (Not null)
   */
  @PostMapping("stop")
  public @NonNull ResponseEntity<ApiResponse<Object>> stopSimulation() {
    log.info("Stop simulation");
    ApiResponse<Object> apiResponse = ticketingService.stopSimulation();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
