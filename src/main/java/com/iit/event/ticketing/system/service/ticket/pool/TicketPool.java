package com.iit.event.ticketing.system.service.ticket.pool;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.core.enums.TicketStatus;
import com.iit.event.ticketing.system.core.model.entity.Ticket;
import com.iit.event.ticketing.system.repository.TicketRepository;
import com.iit.event.ticketing.system.service.ticketing.configurations.TicketingConfiguration;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Ticket Pool
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TicketPool {

  @NonNull
  private final List<Ticket> availableTickets = Collections.synchronizedList(new ArrayList<>());

  @NonNull
  private final TicketingConfiguration ticketingConfiguration;

  @NonNull
  private final TicketRepository ticketRepository;

  /**
   * Add tickets
   *
   * @param vendorId          Vendor Id (Not null)
   * @param ticketsPerRelease Tickets per release
   */
  public synchronized void addTickets(final @NonNull String vendorId, final int ticketsPerRelease) {
    log.debug("Add tickets - Vendor Id: {}; Tickets per release: {};", vendorId, ticketsPerRelease);

    // Check if there is enough capacity in ticket pool to add tickets
    if (ticketingConfiguration.getMaxTicketCapacity() - availableTickets.size() < ticketsPerRelease) {
      log.warn("Adding tickets failed due to insufficient capacity in ticket pool - Vendor Id: {}; Tickets per release: {}; Total tickets: {}; Max capacity: {};",
          vendorId,
          ticketsPerRelease,
          availableTickets.size(),
          ticketingConfiguration.getMaxTicketCapacity()
      );

      return;
    }

    // Add tickets to ticket pool and save in database
    for (int i = 0; i < ticketsPerRelease; i++) {
      Ticket ticket = new Ticket(vendorId);
      availableTickets.add(ticket);
      ticketRepository.save(ticket);
    }

    log.debug("Added tickets - Vendor Id: {}; Tickets per release: {}; Total tickets: {}; Max capacity: {};",
        vendorId,
        ticketsPerRelease,
        availableTickets.size(),
        ticketingConfiguration.getMaxTicketCapacity()
    );

    // Update total tickets ticketing configuration
    ticketingConfiguration.setTotalTickets(availableTickets.size());

    try {
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations - File path: {}; Error: {};", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }
  }

  /**
   * Remove ticket
   *
   * @param customerId Customer Id (Not null)
   */
  public synchronized void removeTicket(final @NonNull String customerId) {
    log.debug("Remove ticket - Customer Id: {};", customerId);

    // Check if there are any tickets in the ticket pool to remove
    if (availableTickets.isEmpty()) {
      log.warn("Removing ticket failed since ticket pool is empty - Customer Id: {};", customerId);
      return;
    }

    // Remove ticket from ticket pool and update database record
    Ticket ticket = availableTickets.removeFirst();
    ticket.setCustomerId(customerId);
    ticket.setStatus(TicketStatus.SOLD);

    ticketRepository.save(ticket);

    log.debug("Removed ticket - Customer Id: {}; Total tickets: {}; Max capacity: {};",
        customerId,
        availableTickets.size(),
        ticketingConfiguration.getMaxTicketCapacity()
    );

    // Update total tickets ticketing configuration
    ticketingConfiguration.setTotalTickets(availableTickets.size());

    try {
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations - File path: {}; Error: {};", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }
  }
}
