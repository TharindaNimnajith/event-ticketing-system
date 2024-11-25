package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.service.ticketing.configurations.TicketingConfiguration;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Configuration Service
 */
@Service
@Slf4j
public class TicketingConfigurationService {

  /**
   * Get ticketing configurations
   *
   * @return ApiResponse containing TicketingConfiguration (Not null)
   */
  public @NonNull ApiResponse<TicketingConfiguration> getTicketingConfigurations() {
    log.debug("Fetching ticketing configurations");

    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
      log.trace("Ticketing configurations fetched successfully - File path: {};\nTicketing configurations:\n{};", TICKETING_CONFIGURATIONS_FILE_PATH, ticketingConfiguration);
      return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations fetched successfully", ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations - File path: {}; Error: {};", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
      return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch ticketing configurations", List.of(ex.getMessage()));
    }
  }
}
