package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Configuration Controller
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Slf4j
public class ConfigurationController {

  private final ConfigurationService configurationService;

  /**
   * Get configurations
   *
   * @return ResponseEntity<TicketingConfiguration>
   */
  @GetMapping
  public ResponseEntity<TicketingConfiguration> getConfigurations() {
    log.info("Get configurations");

    TicketingConfiguration ticketingConfiguration = configurationService.getConfigurations();

    if (ticketingConfiguration != null) {
      return ResponseEntity.ok(ticketingConfiguration);
    } else {
      return ResponseEntity.status(500).body(null);
    }
  }
}
