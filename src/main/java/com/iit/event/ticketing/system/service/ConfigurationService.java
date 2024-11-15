package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Configuration Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationService {

  /**
   * Get ticketing configurations
   *
   * @return ApiResponse containing TicketingConfiguration (Not null)
   */
  public @NonNull ApiResponse<TicketingConfiguration> getConfigurations() {
    log.debug("Get ticketing configurations");
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
    return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations fetched successfully", ticketingConfiguration);
  }

  /**
   * Add ticketing configurations
   *
   * @param ticketingConfiguration TicketingConfiguration
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addConfigurations(final TicketingConfiguration ticketingConfiguration) {
    log.debug("Add ticketing configurations");
    FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations added successfully");
  }
}
