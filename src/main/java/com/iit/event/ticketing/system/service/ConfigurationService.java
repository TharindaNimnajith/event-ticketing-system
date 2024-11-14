package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Configuration Service
 */
@Service
@Slf4j
public class ConfigurationService {

  /**
   * Get configurations
   *
   * @return TicketingConfiguration
   */
  public TicketingConfiguration getConfigurations() {
    log.info("Get configurations");
    return null;
  }
}
