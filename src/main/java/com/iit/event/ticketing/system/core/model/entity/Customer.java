package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.model.TicketingConfiguration;
import com.iit.event.ticketing.system.service.TicketPool;
import com.iit.event.ticketing.system.util.FileUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Customer
 */
@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Slf4j
public class Customer implements Runnable {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @JsonProperty("id")
  @NonNull
  private String id;

  @Column(name = "name", nullable = false)
  @JsonProperty("name")
  @NotBlank
  @NonNull
  private String name;

  @Column(name = "retrieval_interval", nullable = false, updatable = false)
  @JsonProperty("retrieval_interval")
  private int retrievalInterval;

  @Transient
  @JsonIgnore
  @Setter
  private TicketPool ticketPool;

  @Transient
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
        Thread.sleep(Duration.ofSeconds(retrievalInterval));
      } catch (InterruptedException ex) {
        log.error(ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Stop customer thread from running
   */
  public void stop() {
    running = false;
  }
}
