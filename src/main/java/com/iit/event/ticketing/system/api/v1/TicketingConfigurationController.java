package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration;
import com.iit.event.ticketing.system.service.TicketingConfigurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
public class TicketingConfigurationController {

  private final TicketingConfigurationService ticketingConfigurationService;

  /**
   * Add ticketing configurations
   *
   * @param ticketingConfiguration TicketingConfiguration (Not null)
   * @return ResponseEntity containing ApiResponse (Not null)
   */
  @PostMapping
  public @NonNull ResponseEntity<ApiResponse<Object>> addConfigurations(final @RequestBody @Valid @NonNull TicketingConfiguration ticketingConfiguration) {
    log.info("Add ticketing configurations");
    ApiResponse<Object> apiResponse = ticketingConfigurationService.addConfigurations(ticketingConfiguration);
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

  /**
   * Get ticketing configurations
   *
   * @return ResponseEntity containing ApiResponse with TicketingConfiguration (Not null)
   */
  @GetMapping
  public @NonNull ResponseEntity<ApiResponse<TicketingConfiguration>> getConfigurations() {
    log.info("Get ticketing configurations");
    ApiResponse<TicketingConfiguration> apiResponse = ticketingConfigurationService.getConfigurations();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
