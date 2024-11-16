package com.iit.event.ticketing.system.configuration.ticketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ticketing Configuration
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketingConfiguration implements Serializable {

  @JsonProperty("total_tickets")
  @NotNull
  @Positive
  private Integer totalTickets;

  @JsonProperty("ticket_release_rate")
  @NotNull
  @Positive
  private Integer ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  @NotNull
  @Positive
  private Integer customerRetrievalRate;

  @JsonProperty("max_ticket_capacity")
  @NotNull
  @Positive
  private Integer maxTicketCapacity;
}
