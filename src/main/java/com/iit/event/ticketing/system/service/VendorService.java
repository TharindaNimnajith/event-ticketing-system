package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Vendor Service
 */
@Service
@RequiredArgsConstructor
public class VendorService {

  private final List<Vendor> vendors = new ArrayList<>();
  private final TicketPool ticketPool;

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addVendor(final @NonNull Vendor vendor) {
    vendor.setTicketPool(ticketPool);
    vendors.add(vendor);
    return new ApiResponse<>(HttpStatus.OK, "Vendor added successfully");
  }

  /**
   * Get vendors
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendors() {
    return new ApiResponse<>(HttpStatus.OK, "Vendors fetched successfully", vendors);
  }

  /**
   * Get vendors list
   *
   * @return List of vendors (Not null)
   */
  public @NonNull List<Vendor> getVendorsList() {
    return vendors;
  }

  /**
   * Get vendor count
   *
   * @return Vendor count
   */
  public int getVendorCount() {
    return vendors.size();
  }
}
