package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.VendorStatus;
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

  private final TicketPool ticketPool;
  private final VendorRepository vendorRepository;

  @Getter
  private final List<Vendor> activeVendors = new ArrayList<>();

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addVendor(final @NonNull Vendor vendor) {
    if (TicketingService.isStarted()) {
      log.debug("Failed to add vendor since the simulation is currently running");
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to add vendor", List.of("Simulation is currently running"));
    }

    vendor.setTicketPool(ticketPool);
    activeVendors.add(vendor);
    vendorRepository.save(vendor);
    return new ApiResponse<>(HttpStatus.OK, "Vendor added successfully");
  }

  /**
   * Get vendors list
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendorsList() {
    return new ApiResponse<>(HttpStatus.OK, "Vendors fetched successfully", vendorRepository.findAll());
  }

  /**
   * Remove vendor
   *
   * @param vendor Vendor (Not null)
   */
  public void removeVendor(final @NonNull Vendor vendor) {
    log.debug("Deactivate vendor with id: {}; name: {};", vendor.getId(), vendor.getName());
    vendor.setStatus(VendorStatus.INACTIVE);
    activeVendors.remove(vendor);
    vendorRepository.save(vendor);
  }

  /**
   * Get active vendor count
   *
   * @return Active vendor count
   */
  public int getActiveVendorCount() {
    return activeVendors.size();
  }
}
