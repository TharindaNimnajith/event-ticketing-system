package com.iit.event.ticketing.system.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ticketing Configuration
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class TicketingConfiguration implements Serializable {

  @JsonProperty("total_tickets")
  @Setter
  private int totalTickets;

  @JsonProperty("ticket_release_rate")
  private int ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  private int customerRetrievalRate;

  @JsonProperty("max_ticket_capacity")
  private int maxTicketCapacity;
}
