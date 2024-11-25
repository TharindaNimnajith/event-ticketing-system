package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.repository.TicketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticket Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

  @NonNull
  private final TicketRepository ticketRepository;

  /**
   * Get tickets list
   *
   * @return ApiResponse containing List of Ticket objects (Not null)
   */
  public @NonNull ApiResponse<List<Ticket>> getTicketsList() {
    log.debug("Fetching tickets list");
    return new ApiResponse<>(HttpStatus.OK, "Tickets fetched successfully", ticketRepository.findAll());
  }
}
