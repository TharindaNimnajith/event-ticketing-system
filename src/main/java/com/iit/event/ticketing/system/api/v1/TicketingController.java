package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.service.TicketingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticketing Controller
 */
@RestController
@RequestMapping("/ticketing")
@RequiredArgsConstructor
@Slf4j
public class TicketingController {

  private final TicketingService ticketingService;

  /**
   * Start
   *
   * @return ResponseEntity<String>
   */
  @PostMapping("/start")
  public ResponseEntity<String> start() {
    log.info("Start");
    ticketingService.start();
    return ResponseEntity.ok("Started");
  }

  /**
   * Stop
   *
   * @return ResponseEntity<String>
   */
  @PostMapping("/stop")
  public ResponseEntity<String> stop() {
    log.info("Stop");
    ticketingService.stop();
    return ResponseEntity.ok("Stopped");
  }
}
