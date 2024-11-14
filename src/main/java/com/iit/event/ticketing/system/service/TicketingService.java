package com.iit.event.ticketing.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Ticketing Service
 */
@Service
@Slf4j
public class TicketingService {

  /**
   * Start
   */
  public void start() {
    log.info("Started");
  }

  /**
   * Stop
   */
  public void stop() {
    log.info("Stopped");
  }
}
