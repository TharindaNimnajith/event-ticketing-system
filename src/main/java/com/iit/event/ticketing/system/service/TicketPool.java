package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.entity.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Ticket Pool
 */
@Component
@Slf4j
public class TicketPool {

  private final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());

  /**
   * Add tickets
   */
  public void addTickets() {
    log.debug("Add tickets");
  }

  /**
   * Remove ticket
   */
  public void removeTicket() {
    log.debug("Remove ticket");
  }
}
