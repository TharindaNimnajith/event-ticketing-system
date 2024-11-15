package com.iit.event.ticketing.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Event Ticketing Application
 */
@SpringBootApplication
@Slf4j
public class EventTicketingApplication {

  /**
   * Main method of the Spring Boot application
   *
   * @param args The command line arguments passed to the Spring Boot application
   */
  public static void main(final String[] args) {
    SpringApplication.run(EventTicketingApplication.class, args);
  }
}
