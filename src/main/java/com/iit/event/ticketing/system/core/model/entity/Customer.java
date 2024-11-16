package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.TicketPool;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * Customer
 */
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
public class Customer implements Runnable {

  private final TicketPool ticketPool;

  @JsonProperty("customer_id")
  @NotNull
  @NonNull
  private String customerId;

  @JsonProperty("retrieval_interval")
  @NonNull
  @Positive
  private Integer retrievalInterval;

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.debug("Customer run");

    //    while (true) {
    //      String ticket = ticketPool.removeTicket();
    //      if (ticket != null) {
    //        System.out.println("Customer purchased: " + ticket);
    //      } else {
    //        System.out.println("No tickets available.");
    //      }
    //      try {
    //        Thread.sleep(1000); // Attempt to buy tickets at intervals
    //      } catch (InterruptedException e) {
    //        Thread.currentThread().interrupt();
    //      }
    //    }
  }
}
