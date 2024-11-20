package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.TicketPool;
import com.iit.event.ticketing.system.util.FileUtils;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Customer
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Customer implements Runnable {

  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("name")
  @NotBlank
  @NonNull
  private String name;

  @JsonProperty("retrieval_interval")
  private int retrievalInterval;

  @JsonIgnore
  @Setter
  private TicketPool ticketPool;

  @JsonIgnore
  private volatile boolean running;

  /**
   * Customer constructor
   *
   * @param name Name (Not null)
   * @throws IOException IOException
   */
  @JsonCreator
  public Customer(final @NonNull String name) throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

    this.id = UUID.randomUUID().toString();
    this.name = StringUtils.trim(name);
    this.retrievalInterval = ticketingConfiguration.getCustomerRetrievalRate();
    this.running = true;
  }

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    while (running) {
      ticketPool.removeTicket(id);

      try {
        Thread.sleep(retrievalInterval);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Stop
   */
  public void stop() {
    running = false;
  }
}
