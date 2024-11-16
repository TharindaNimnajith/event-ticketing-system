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
 * Vendor
 */
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
public class Vendor implements Runnable {

  private final TicketPool ticketPool;

  @JsonProperty("vendor_id")
  @NotNull
  @NonNull
  private String vendorId;

  @JsonProperty("tickets_per_release")
  @NotNull
  @NonNull
  @Positive
  private Integer ticketsPerRelease;

  @JsonProperty("release_interval")
  @NonNull
  @Positive
  private Integer releaseInterval;

  /**
   * Execute vendor functionality
   */
  @Override
  public void run() {
    log.debug("Vendor run");

    //    while (true) {
    //      ticketPool.addTickets(ticketReleaseRate);
    //      try {
    //        Thread.sleep(1000); // Release tickets at intervals
    //      } catch (InterruptedException e) {
    //        Thread.currentThread().interrupt();
    //      }
    //    }
  }
}
