package com.iit.event.ticketing.system.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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

  private String vendorId;
  private int ticketsPerRelease;
  private int releaseInterval;

  /**
   * Execute vendor functionality
   */
  @Override
  public void run() {
    log.info("Vendor run");
  }
}
