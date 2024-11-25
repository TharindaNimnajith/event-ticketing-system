package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.ticket.pool.TicketPool;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
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
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @JsonProperty("id")
  @NotBlank
  @NonNull
  private String id;

  @Column(name = "retrieval_interval", nullable = false, updatable = false)
  @JsonProperty("retrieval_interval")
  @Setter
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
   * @param id Id (Not null)
   */
  @JsonCreator
  public Customer(final @NonNull String id) {
    log.debug("Creating customer - Id: {};", id);
    this.id = StringUtils.trim(id);
  }

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.debug("Running customer thread begin - Id: {};", this.id);

    // Set running flag to true once customer thread is started
    running = true;

    // Retrieve tickets in a loop until the customer thread is stopped or interrupted
    while (running) {
      ticketPool.removeTicket(this.id);

      try {
        Thread.sleep(Duration.ofSeconds(this.retrievalInterval));
      } catch (InterruptedException ex) {
        log.error("Customer thread is interrupted while sleeping - Id: {}; Error: {};", this.id, ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }

    log.debug("Running customer thread end - Id: {};", this.id);
  }

  /**
   * Stop customer thread from running
   */
  public void stop() {
    log.debug("Stop customer thread - Id: {};", this.id);
    running = false;
  }
}
