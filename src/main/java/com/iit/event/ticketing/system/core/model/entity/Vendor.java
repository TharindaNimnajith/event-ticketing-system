package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.enums.VendorStatus;
import com.iit.event.ticketing.system.service.TicketPool;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Vendor
 */
@Entity
@Table(name = "vendors")
@NoArgsConstructor
@Getter
@Slf4j
public class Vendor implements Runnable {

  @Id
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @JsonProperty("id")
  @NotBlank
  @NonNull
  private String id;

  @Column(name = "tickets_per_release", nullable = false)
  @JsonProperty("tickets_per_release")
  @NotNull
  @NonNull
  @Positive
  private Integer ticketsPerRelease;

  @Column(name = "release_interval", nullable = false, updatable = false)
  @JsonProperty("release_interval")
  @Setter
  private int releaseInterval;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @JsonProperty("status")
  @NonNull
  @Setter
  private VendorStatus status;

  @Transient
  @JsonIgnore
  @Setter
  private TicketPool ticketPool;

  @Transient
  @JsonIgnore
  private volatile boolean running;

  /**
   * Vendor constructor
   *
   * @param id                Id (Not null)
   * @param ticketsPerRelease Tickets per release (Not null)
   */
  @JsonCreator
  public Vendor(final @NonNull String id, final @NonNull Integer ticketsPerRelease) {
    log.debug("Creating vendor - Id: {}; Tickets per release: {};", id, ticketsPerRelease);

    this.id = StringUtils.trim(id);
    this.ticketsPerRelease = ticketsPerRelease;
    this.status = VendorStatus.ACTIVE;

    running = true;
  }

  /**
   * Execute vendor functionality
   */
  @Override
  public void run() {
    log.debug("Running vendor thread begin - Id: {};", this.id);

    while (running) {
      ticketPool.addTickets(this.id, this.ticketsPerRelease);

      try {
        Thread.sleep(Duration.ofSeconds(this.releaseInterval));
      } catch (InterruptedException ex) {
        log.error("Vendor thread is interrupted while sleeping - Id: {}; Error: {};", this.id, ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }

    log.debug("Running vendor thread end - Id: {};", this.id);
  }

  /**
   * Stop vendor thread from running
   */
  public void stop() {
    log.debug("Stop vendor thread - Id: {};", this.id);
    running = false;
  }
}
