package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.repository.VendorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
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

  private final TicketPool ticketPool;
  private final VendorRepository vendorRepository;

  @Getter
  private final List<Vendor> vendors = new ArrayList<>();

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addVendor(final @NonNull Vendor vendor) {
    vendor.setTicketPool(ticketPool);
    vendors.add(vendor);
    vendorRepository.save(vendor);
    return new ApiResponse<>(HttpStatus.OK, "Vendor added successfully");
  }

  /**
   * Get vendors list
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendorsList() {
    return new ApiResponse<>(HttpStatus.OK, "Vendors fetched successfully", vendors);
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
