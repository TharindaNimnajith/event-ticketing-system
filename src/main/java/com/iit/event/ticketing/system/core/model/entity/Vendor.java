package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * Vendor
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
public class Vendor implements Runnable {

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
  }
}
