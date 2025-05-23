package com.iit.event.ticketing.system.core.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iit.event.ticketing.system.core.enums.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * Ticket
 */
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Getter
@Slf4j
public class Ticket {

  @Id
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @JsonProperty("id")
  @NonNull
  private String id;

  @Column(name = "vendor_id", nullable = false, updatable = false)
  @JsonProperty("vendor_id")
  @NonNull
  private String vendorId;

  @Column(name = "customer_id")
  @JsonProperty("customer_id")
  @Setter
  private String customerId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @JsonProperty("status")
  @NonNull
  @Setter
  private TicketStatus status;

  /**
   * Ticket constructor
   *
   * @param vendorId Vendor Id (Not null)
   */
  public Ticket(final @NonNull String vendorId) {
    log.debug("Creating ticket - Vendor Id: {};", vendorId);

    this.id = UUID.randomUUID().toString();
    this.vendorId = vendorId;
    this.status = TicketStatus.AVAILABLE;
  }
}
