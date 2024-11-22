package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.model.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.VendorStatus;
import com.iit.event.ticketing.system.service.TicketPool;
import com.iit.event.ticketing.system.util.FileUtils;
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
 * Vendor
 */
@Entity
@Table(name = "vendors")
@NoArgsConstructor
@Getter
@Slf4j
public class Vendor implements Runnable {

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

  @Column(name = "tickets_per_release", nullable = false)
  @JsonProperty("tickets_per_release")
  @NotNull
  @NonNull
  @Positive
  private Integer ticketsPerRelease;

  @Column(name = "release_interval", nullable = false, updatable = false)
  @JsonProperty("release_interval")
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
   * @param name              Name (Not null)
   * @param ticketsPerRelease Tickets per release (Not null)
   * @throws IOException IOException
   */
  @JsonCreator
  public Vendor(final @NonNull String name, final @NonNull Integer ticketsPerRelease) throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

    this.id = UUID.randomUUID().toString();
    this.name = StringUtils.trim(name);
    this.ticketsPerRelease = ticketsPerRelease;
    this.releaseInterval = ticketingConfiguration.getTicketReleaseRate();
    this.status = VendorStatus.ACTIVE;
    this.running = true;
  }

  /**
   * Execute vendor functionality
   */
  @Override
  public void run() {
    while (running) {
      ticketPool.addTickets(id, ticketsPerRelease);

      try {
        Thread.sleep(Duration.ofSeconds(releaseInterval));
      } catch (InterruptedException ex) {
        log.error(ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Stop vendor thread from running
   */
  public void stop() {
    running = false;
  }
}
