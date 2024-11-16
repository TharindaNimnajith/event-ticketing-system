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
  //  private int maxCapacity;
  //
  //  public TicketPool(int maxCapacity) {
  //    this.maxCapacity = maxCapacity;
  //  }

  /**
   * Add tickets
   */
  public synchronized void addTickets() {
    log.debug("Add tickets");

    //    for (int i = 0; i < ticketCount; i++) {
    //      if (tickets.size() < 100) {
    //        tickets.add("Ticket" + (tickets.size() + 1));
    //        System.out.println("Ticket added. Total tickets: " + tickets.size());
    //      } else {
    //        System.out.println("Maximum ticket capacity reached!");
    //        break;
    //      }
    //    }
  }

  /**
   * Remove ticket
   */
  public synchronized void removeTicket() {
    log.debug("Remove ticket");

    //    if (!tickets.isEmpty()) {
    //      String ticket = tickets.remove(0);
    //      System.out.println("Ticket purchased: " + ticket);
    //    }
  }

  //  /**
  //   * Get ticket count
  //   *
  //   * @return Ticket count in the ticket pool
  //   */
  //  public int getTicketCount() {
  //    return tickets.size();
  //  }
}
