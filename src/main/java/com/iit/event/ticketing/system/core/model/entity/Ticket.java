package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.model.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * Ticket
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {

  @JsonProperty("ticket_id")
  @NotNull
  @NonNull
  private String ticketId;

  @JsonProperty("vendor_id")
  @NotNull
  @NonNull
  private String vendorId;

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("ticket_status")
  @NotNull
  @NonNull
  private TicketStatus ticketStatus;

  /**
   * Ticket constructor
   *
   * @param ticketId Ticket ID
   * @param vendorId Vendor ID
   */
  public Ticket(final @NonNull String ticketId, final @NonNull String vendorId) {
    this.ticketId = ticketId;
    this.vendorId = vendorId;
    this.ticketStatus = TicketStatus.AVAILABLE;
  }
}
