package com.iit.event.ticketing.system.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Customer
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class Customer implements Runnable {

  private String customerId;
  private int retrievalInterval;

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.info("Customer run");
  }
}
