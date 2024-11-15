package com.iit.event.ticketing.system.configuration.ticketing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ticketing Configurations
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketingConfiguration implements Serializable {

  @JsonProperty("total_tickets")
  private Integer totalTickets;

  @JsonProperty("ticket_release_rate")
  private Integer ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  private Integer customerRetrievalRate;

  @JsonProperty("max_ticket_capacity")
  private Integer maxTicketCapacity;
}
