package com.iit.event.ticketing.system.core.model.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Ticket Pool
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
public class TicketPool {

  private List<Ticket> ticketList = new ArrayList<>();

  /**
   * Add tickets
   */
  public void addTickets() {
    log.info("Add tickets");
  }

  /**
   * Remove ticket
   */
  public void removeTicket() {
    log.info("Remove ticket");
  }
}
