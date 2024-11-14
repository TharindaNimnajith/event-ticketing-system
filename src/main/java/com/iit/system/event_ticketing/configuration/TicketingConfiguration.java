package com.iit.system.event_ticketing.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ticketing")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketingConfiguration {

  private int totalTickets;
  private int ticketReleaseRate;
  private int customerRetrievalRate;
  private int maxTicketCapacity;
}
