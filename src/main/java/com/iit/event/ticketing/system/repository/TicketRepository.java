package com.iit.event.ticketing.system.repository;

import com.iit.event.ticketing.system.core.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ticket Repository
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

  // No custom methods
}
