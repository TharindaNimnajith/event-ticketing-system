package com.iit.system.event_ticketing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class Customer implements Runnable {

  private String customerId;
  private int retrievalInterval;

  @Override
  public void run() {
    log.info("Customer run");
  }
}
