package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.TicketingConfiguration;
import com.iit.event.ticketing.system.core.enums.VendorStatus;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.repository.VendorRepository;
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

    // Check if simulation is not running before adding the new vendor
    if (TicketingService.isStarted()) {
      log.error("Failed to add vendor since the simulation is currently running - Id: {};", vendor.getId());

      return new ApiResponse<>(
          HttpStatus.CONFLICT,
          "Failed to add vendor",
          List.of("Simulation is currently running")
      );
    }

    // Set release interval ticketing configuration and ticket pool
    vendor.setReleaseInterval(ticketingConfiguration.getTicketReleaseRate());
    vendor.setTicketPool(ticketPool);

    // Add vendor to active vendors list and save in database
    activeVendors.add(vendor);
    vendorRepository.save(vendor);

    return new ApiResponse<>(
        HttpStatus.OK,
        "Vendor added successfully"
    );
  }

  /**
   * Get vendors list
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendorsList() {
    log.debug("Fetching vendors list");

    return new ApiResponse<>(
        HttpStatus.OK,
        "Vendors fetched successfully",
        vendorRepository.findAll()
    );
  }

  /**
   * Remove vendor
   *
   * @param vendor Vendor (Not null)
   */
  public void removeVendor(final @NonNull Vendor vendor) {
    log.debug("Remove vendor - Id: {};", vendor.getId());

    // Remove from active vendors list
    activeVendors.remove(vendor);

    // Deactivate vendor status and update database record
    vendor.setStatus(VendorStatus.INACTIVE);
    vendorRepository.save(vendor);
  }

  /**
   * Get active vendor count
   *
   * @return Active vendor count
   */
  public int getActiveVendorCount() {
    log.debug("Get active vendor count");
    return activeVendors.size();
  }
}
