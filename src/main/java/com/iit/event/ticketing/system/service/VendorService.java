package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.enums.VendorStatus;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.repository.VendorRepository;
import com.iit.event.ticketing.system.service.ticket.pool.TicketPool;
import com.iit.event.ticketing.system.service.ticketing.configurations.TicketingConfiguration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Vendor Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VendorService {

  @NonNull
  private final TicketingConfiguration ticketingConfiguration;

  @NonNull
  private final TicketPool ticketPool;

  @NonNull
  private final VendorRepository vendorRepository;

  @NonNull
  @Getter
  private final List<Vendor> activeVendors = new ArrayList<>();

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addVendor(final @NonNull Vendor vendor) {
    log.debug("Adding vendor - Id: {};", vendor.getId());

    // Check if tickets per release is less than or equal to max ticket capacity
    if (vendor.getTicketsPerRelease() > ticketingConfiguration.getMaxTicketCapacity()) {
      log.error("Failed to add vendor since tickets per release is greater than max ticket capacity - Id: {}; Tickets per release: {}; Max ticket capacity: {};",
          vendor.getId(),
          vendor.getTicketsPerRelease(),
          ticketingConfiguration.getMaxTicketCapacity()
      );

      return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Failed to add vendor", List.of("Tickets per release is greater than max ticket capacity"));
    }

    // Check if the vendor already exists in the database
    if (vendorRepository.existsById(vendor.getId())) {
      log.error("Failed to add vendor since a vendor with the same Id already exists in the database - Id: {};", vendor.getId());
      return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Failed to add vendor", List.of("A vendor with the same Id already exists in the database"));
    }

    // Check if simulation is not running before adding the new vendor
    if (TicketingService.isStarted()) {
      log.error("Failed to add vendor since the simulation is currently running - Id: {};", vendor.getId());
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to add vendor", List.of("Simulation is currently running"));
    }

    // Set release interval ticketing configuration and ticket pool
    vendor.setReleaseInterval(ticketingConfiguration.getTicketReleaseRate());
    vendor.setTicketPool(ticketPool);

    // Add vendor to active vendors list and save in database
    activeVendors.add(vendor);
    vendorRepository.save(vendor);

    log.trace("Added vendor - Id: {};", vendor.getId());

    return new ApiResponse<>(HttpStatus.CREATED, "Vendor added successfully");
  }

  /**
   * Get vendors list
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendorsList() {
    log.debug("Fetching vendors list");
    return new ApiResponse<>(HttpStatus.OK, "Vendors fetched successfully", vendorRepository.findAll());
  }

  /**
   * Remove vendor
   *
   * @param vendor Vendor (Not null)
   */
  public void removeVendor(final @NonNull Vendor vendor) {
    log.debug("Removing vendor - Id: {};", vendor.getId());

    // Remove from active vendors list
    activeVendors.remove(vendor);

    // Deactivate vendor status and update database record
    vendor.setStatus(VendorStatus.INACTIVE);
    vendorRepository.save(vendor);

    log.trace("Removed vendor - Id: {};", vendor.getId());
  }
}
