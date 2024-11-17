package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.service.TicketPool;
import com.iit.event.ticketing.system.util.FileUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.IOException;
import java.util.UUID;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Vendor
 */
@Getter
public class Vendor implements Runnable {

  private final TicketPool ticketPool;

  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("name")
  @NotBlank
  @NonNull
  private String name;

  @JsonProperty("tickets_per_release")
  @NotNull
  @NonNull
  @Positive
  private Integer ticketsPerRelease;

  @JsonProperty("release_interval")
  @Positive
  private int releaseInterval;

  /**
   * Vendor constructor
   *
   * @param name              Name
   * @param ticketsPerRelease Tickets per release
   * @param ticketPool        TicketPool
   * @throws IOException IOException
   */
  public Vendor(final @NonNull String name, final @NonNull Integer ticketsPerRelease, final @NonNull TicketPool ticketPool) throws IOException {
    TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

    this.id = UUID.randomUUID().toString();
    this.name = StringUtils.trim(name);
    this.ticketsPerRelease = ticketsPerRelease;
    this.releaseInterval = ticketingConfiguration.getTicketReleaseRate();
    this.ticketPool = ticketPool;
  }

  /**
   * Execute vendor functionality
   */
  @Override
  public void run() {
    // TODO
  }
}
