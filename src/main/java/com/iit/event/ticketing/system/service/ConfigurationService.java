package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIG_FILE_PATH;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.util.FileUtils;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.io.IOException;
import java.util.List;
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

    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
      return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations fetched successfully", ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIG_FILE_PATH, ex.getMessage(), ex);
      return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch ticketing configurations", List.of(ex.getMessage()));
    }
  }

  /**
   * Add ticketing configurations
   *
   * @param ticketingConfiguration TicketingConfiguration
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addConfigurations(final @NonNull TicketingConfiguration ticketingConfiguration) {
    log.debug("Add ticketing configurations");

    List<String> errors = ValidationUtils.validateTicketingConfigurations(ticketingConfiguration);

    if (!errors.isEmpty()) {
      return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Ticketing configuration validations failed", errors);
    }

    try {
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
      return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations added successfully");
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations to file ({}) - Error: {}", TICKETING_CONFIG_FILE_PATH, ex.getMessage(), ex);
      return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save ticketing configurations", List.of(ex.getMessage()));
    }
  }
}
