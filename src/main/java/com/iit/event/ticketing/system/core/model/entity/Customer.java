package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
 * Customer
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
public class Customer implements Runnable {

  @JsonProperty("customer_id")
  @NotNull
  @NonNull
  private String customerId;

  @JsonProperty("retrieval_interval")
  @NonNull
  @Positive
  private Integer retrievalInterval;

  /**
   * Execute customer functionality
   */
  @Override
  public void run() {
    log.debug("Customer run");
  }
}
