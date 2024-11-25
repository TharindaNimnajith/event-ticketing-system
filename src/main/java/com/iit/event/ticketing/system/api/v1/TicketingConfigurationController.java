package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.service.TicketingConfigurationService;
import com.iit.event.ticketing.system.service.ticketing.configurations.TicketingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticketing Configuration Controller
 */
@RestController
@RequestMapping("v1/configs")
@RequiredArgsConstructor
@Slf4j
public class TicketingConfigurationController {

  @NonNull
  private final TicketingConfigurationService ticketingConfigurationService;

  /**
   * Get ticketing configurations
   *
   * @return ResponseEntity containing ApiResponse with TicketingConfiguration (Not null)
   */
  @GetMapping
  public @NonNull ResponseEntity<ApiResponse<TicketingConfiguration>> getTicketingConfigurations() {
    log.info("Get ticketing configurations");
    ApiResponse<TicketingConfiguration> apiResponse = ticketingConfigurationService.getTicketingConfigurations();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
