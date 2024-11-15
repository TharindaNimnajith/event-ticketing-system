package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticketing Configuration Controller
 */
@RestController
@RequestMapping("v1/configs")
@RequiredArgsConstructor
@Slf4j
public class ConfigurationController {

  private final ConfigurationService configurationService;

  /**
   * Get ticketing configurations
   *
   * @return ResponseEntity containing ApiResponse with TicketingConfiguration
   */
  @GetMapping
  public ResponseEntity<ApiResponse<TicketingConfiguration>> getConfigurations() {
    log.info("Get ticketing configurations");
    ApiResponse<TicketingConfiguration> apiResponse = configurationService.getConfigurations();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

  /**
   * Add ticketing configurations
   *
   * @param ticketingConfiguration TicketingConfiguration
   * @return ResponseEntity containing ApiResponse
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Object>> addConfigurations(final @RequestBody TicketingConfiguration ticketingConfiguration) {
    log.info("Add ticketing configurations");
    ApiResponse<Object> apiResponse = configurationService.addConfigurations(ticketingConfiguration);
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
