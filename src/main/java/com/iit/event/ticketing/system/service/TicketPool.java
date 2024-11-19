package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.TicketStatus;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ticket Pool
 */
@Component
public class TicketPool {

  private final List<Ticket> tickets;
  private final int maxCapacity;

  /**
   * TicketPool constructor
   *
   * @throws IOException IOException
   */
  public TicketPool() throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

    this.tickets = Collections.synchronizedList(new ArrayList<>());
    this.maxCapacity = ticketingConfiguration.getMaxTicketCapacity();
  }

  /**
   * Add tickets
   */
  public synchronized void addTickets(final @NonNull String vendorId, final int ticketsPerRelease) {
    if (maxCapacity - tickets.size() >= ticketsPerRelease) {
      for (int i = 0; i < ticketsPerRelease; i++) {
        Ticket ticket = new Ticket(vendorId);
        tickets.add(ticket);
      }
    }
  }

  /**
   * Remove ticket
   */
  public synchronized void removeTicket(final @NonNull String customerId) {
    if (!tickets.isEmpty()) {
      tickets.removeFirst();
    }
  }

  /**
   * Get available ticket count
   *
   * @return Available ticket count
   */
  public int getAvailableTicketCount() {
    synchronized (tickets) {
      return (int) tickets.stream()
          .filter(ticket -> ticket.getStatus() == TicketStatus.AVAILABLE)
          .count();
    }
  }
}
