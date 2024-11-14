package com.iit.system.event_ticketing;

import com.iit.system.event_ticketing.configuration.TicketingConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TicketingConfiguration.class)
@Slf4j
public class EventTicketingApplication {

  public static void main(final String[] args) {
    SpringApplication.run(EventTicketingApplication.class, args);
  }
}
