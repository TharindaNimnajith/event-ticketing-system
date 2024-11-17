package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.model.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * Ticket
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Ticket {

  @JsonProperty("id")
  @NotNull
  @NonNull
  private String id;

  @JsonProperty("vendor_id")
  @NotNull
  @NonNull
  private String vendorId;

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("status")
  @NotNull
  @NonNull
  private TicketStatus status;

  /**
   * Ticket constructor
   *
   * @param id       ID
   * @param vendorId Vendor ID
   */
  public Ticket(final @NonNull String id, final @NonNull String vendorId) {
    this.id = id;
    this.vendorId = vendorId;
    this.status = TicketStatus.AVAILABLE;
  }
}
