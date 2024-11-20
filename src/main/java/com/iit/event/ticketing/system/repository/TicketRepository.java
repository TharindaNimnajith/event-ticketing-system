package com.iit.event.ticketing.system.repository;

import com.iit.event.ticketing.system.core.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Ticket Repository
 */
public interface TicketRepository extends JpaRepository<Ticket, String> {

}
