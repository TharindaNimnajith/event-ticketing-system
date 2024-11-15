package com.iit.event.ticketing.system.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ticket
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {

  private String ticketId;
}
