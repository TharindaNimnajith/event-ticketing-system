package com.iit.event.ticketing.system.configuration.ticketing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Ticketing Configurations
 */
@Configuration
@ConfigurationProperties(prefix = "ticketing")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
