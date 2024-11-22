package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.TicketStatus;
import com.iit.event.ticketing.system.core.model.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.repository.TicketRepository;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ticket Pool
 */
@Component
@Slf4j
public class TicketPool {

  private final List<Ticket> availableTickets = Collections.synchronizedList(new ArrayList<>());
  private final int maxCapacity;

  private final TicketRepository ticketRepository;

  /**
   * TicketPool constructor
   *
   * @param ticketRepository TicketRepository (Not null)
   * @throws IOException IOException
   */
  public TicketPool(final @NonNull TicketRepository ticketRepository) throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
    this.maxCapacity = ticketingConfiguration.getMaxTicketCapacity();

    this.ticketRepository = ticketRepository;
  }

  /**
   * Add tickets
   *
   * @param vendorId          Vendor ID (Not null)
   * @param ticketsPerRelease Tickets per release
   */
  public synchronized void addTickets(final @NonNull String vendorId, final int ticketsPerRelease) {
    if (maxCapacity - availableTickets.size() < ticketsPerRelease) {
      log.error("Not enough capacity to add tickets - Max capacity: {}; Total tickets: {}; Tickets per release: {};",
          maxCapacity,
          availableTickets.size(),
          ticketsPerRelease
      );

      return;
    }

    for (int i = 0; i < ticketsPerRelease; i++) {
      Ticket ticket = new Ticket(vendorId);
      availableTickets.add(ticket);
      ticketRepository.save(ticket);
    }

    log.debug("Add tickets - Vendor Id: {}; Tickets per release: {}; Total tickets: {};",
        vendorId,
        ticketsPerRelease,
        availableTickets.size()
    );
  }

  /**
   * Remove ticket
   *
   * @param customerId Customer ID (Not null)
   */
  public synchronized void removeTicket(final @NonNull String customerId) {
    if (availableTickets.isEmpty()) {
      log.error("Remove ticket failed - Ticket pool is empty - Customer Id: {}", customerId);
      return;
    }

    Ticket ticket = availableTickets.removeFirst();
    ticket.setCustomerId(customerId);
    ticket.setStatus(TicketStatus.SOLD);
    ticketRepository.save(ticket);

    log.debug("Remove ticket - Customer Id: {}; Total tickets: {};", customerId, availableTickets.size());
  }

  /**
   * Get available ticket count
   *
   * @return Available ticket count
   */
  public int getAvailableTicketCount() {
    synchronized (availableTickets) {
      return availableTickets.size();
    }
  }
}
