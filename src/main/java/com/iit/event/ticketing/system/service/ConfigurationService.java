package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Configuration Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationService {

  private final TicketingConfiguration ticketingConfiguration;

  /**
   * Get configurations
   *
   * @return TicketingConfiguration
   */
  public TicketingConfiguration getConfigurations() {
    log.info("Get configurations");

    return TicketingConfiguration.builder()
        .totalTickets(ticketingConfiguration.getTotalTickets())
        .ticketReleaseRate(ticketingConfiguration.getTicketReleaseRate())
        .customerRetrievalRate(ticketingConfiguration.getCustomerRetrievalRate())
        .maxTicketCapacity(ticketingConfiguration.getMaxTicketCapacity())
        .build();
  }
}
