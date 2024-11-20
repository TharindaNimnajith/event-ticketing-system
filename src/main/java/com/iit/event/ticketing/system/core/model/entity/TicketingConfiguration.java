package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Ticketing Configuration
 */
@Getter
@Setter
public class TicketingConfiguration implements Serializable {

  @JsonProperty("total_tickets")
  private int totalTickets;

  @JsonProperty("ticket_release_rate")
  private int ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  private int customerRetrievalRate;

  @JsonProperty("max_ticket_capacity")
  private int maxTicketCapacity;
}
