package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.service.TicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ticket Controller
 */
@RestController
@RequestMapping("v1/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

  @NonNull
  private final TicketService ticketService;

  /**
   * Get tickets list
   *
   * @return ResponseEntity containing ApiResponse with List of Ticket objects (Not null)
   */
  @GetMapping
  public @NonNull ResponseEntity<ApiResponse<List<Ticket>>> getTicketsList() {
    log.info("Get tickets list");
    ApiResponse<List<Ticket>> apiResponse = ticketService.getTicketsList();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
