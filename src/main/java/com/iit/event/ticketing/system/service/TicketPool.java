package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.TicketStatus;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
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
  private final int maxCapacity;

  /**
   * TicketPool constructor
   *
   * @throws IOException IOException
   */
  public TicketPool() throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
    this.maxCapacity = ticketingConfiguration.getMaxTicketCapacity();
  }

  /**
   * Add tickets
   */
  public synchronized void addTickets() {
    log.debug("Add tickets");
  }

  /**
   * Remove ticket
   */
  public synchronized void removeTicket() {
    log.debug("Remove ticket");
  }

  /**
   * Get available ticket count
   *
   * @return Available ticket count
   */
  public synchronized int getAvailableTicketCount() {
    synchronized (tickets) {
      return (int) tickets.stream()
          .filter(ticket -> ticket.getStatus() == TicketStatus.AVAILABLE)
          .count();
    }
  }
}
