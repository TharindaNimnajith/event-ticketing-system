package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
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

  private final TicketingConfiguration ticketingConfiguration;

  /**
   * Get ticketing configurations
   *
   * @return ApiResponse containing TicketingConfiguration
   */
  public @NonNull ApiResponse<TicketingConfiguration> getConfigurations() {
    log.info("Get ticketing configurations");

    TicketingConfiguration ticketingConfig = TicketingConfiguration.builder()
        .totalTickets(ticketingConfiguration.getTotalTickets())
        .ticketReleaseRate(ticketingConfiguration.getTicketReleaseRate())
        .customerRetrievalRate(ticketingConfiguration.getCustomerRetrievalRate())
        .maxTicketCapacity(ticketingConfiguration.getMaxTicketCapacity())
        .build();

    return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations fetched successfully", ticketingConfig);
  }

  /**
   * Add ticketing configurations
   *
   * @param ticketingConfig TicketingConfiguration
   * @return ApiResponse
   */
  public @NonNull ApiResponse<Object> addConfigurations(final TicketingConfiguration ticketingConfig) {
    log.info("Add ticketing configurations");

    ticketingConfiguration.setTotalTickets(ticketingConfig.getTotalTickets());
    ticketingConfiguration.setTicketReleaseRate(ticketingConfig.getTicketReleaseRate());
    ticketingConfiguration.setCustomerRetrievalRate(ticketingConfig.getCustomerRetrievalRate());
    ticketingConfiguration.setMaxTicketCapacity(ticketingConfig.getMaxTicketCapacity());

    return new ApiResponse<>(HttpStatus.OK, "Ticketing configurations added successfully");
  }
}
