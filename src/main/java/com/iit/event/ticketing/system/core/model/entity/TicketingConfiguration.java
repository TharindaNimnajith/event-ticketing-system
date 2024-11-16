package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

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
  @NonNull
  @Positive
  private Integer totalTickets;

  @JsonProperty("ticket_release_rate")
  @NotNull
  @NonNull
  @Positive
  private Integer ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  @NotNull
  @NonNull
  @Positive
  private Integer customerRetrievalRate;

  @JsonProperty("max_ticket_capacity")
  @NotNull
  @NonNull
  @Positive
  private Integer maxTicketCapacity;
}
