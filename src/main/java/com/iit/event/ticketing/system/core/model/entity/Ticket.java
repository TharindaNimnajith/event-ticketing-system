package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.model.TicketStatus;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * Ticket
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Ticket {

  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("vendor_id")
  @NonNull
  private String vendorId;

  @JsonProperty("customer_id")
  @Setter
  private String customerId;

  @JsonProperty("status")
  @NonNull
  @Setter
  private TicketStatus status;

  /**
   * Ticket constructor
   *
   * @param vendorId Vendor ID (Not null)
   */
  public Ticket(final @NonNull String vendorId) {
    this.id = UUID.randomUUID().toString();
    this.vendorId = vendorId;
    this.status = TicketStatus.AVAILABLE;
  }
}
