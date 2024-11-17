package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.TicketPool;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Slf4j
public class Customer implements Runnable {

  private final TicketPool ticketPool;

  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("name")
  @NotNull
  @NonNull
  private String name;

  @JsonProperty("retrieval_interval")
  @Positive
  @Setter
  private int retrievalInterval;

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.debug("Customer run");
  }
}
