package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.TicketPool;
import com.iit.event.ticketing.system.util.FileUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.io.IOException;
import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Customer
 */
@Getter
@Slf4j
public class Customer implements Runnable {

  private final TicketPool ticketPool;

  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("name")
  @NotBlank
  @NonNull
  private String name;

  @JsonProperty("retrieval_interval")
  @Positive
  private int retrievalInterval;

  /**
   * Customer constructor
   *
   * @param name       Name
   * @param ticketPool TicketPool
   * @throws IOException IOException
   */
  public Customer(final @NonNull String name, final @NonNull TicketPool ticketPool) throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

    this.id = UUID.randomUUID().toString();
    this.name = StringUtils.trim(name);
    this.retrievalInterval = ticketingConfiguration.getCustomerRetrievalRate();
    this.ticketPool = ticketPool;
  }

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.debug("Customer run");
  }
}
