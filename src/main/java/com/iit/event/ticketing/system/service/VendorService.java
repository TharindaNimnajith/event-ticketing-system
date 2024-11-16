package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Vendor Service
 */
@Service
@Slf4j
public class VendorService {

  private final List<Vendor> vendors = new ArrayList<>();

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addVendor(final @NonNull Vendor vendor) {
    log.debug("Add vendor");

    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
      vendor.setReleaseInterval(ticketingConfiguration.getTicketReleaseRate());
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
      return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch ticketing configurations", List.of(ex.getMessage()));
    }

    vendors.add(vendor);
    return new ApiResponse<>(HttpStatus.OK, "Vendor added successfully");
  }

  /**
   * Get vendors
   *
   * @return ApiResponse containing List of Vendor objects (Not null)
   */
  public @NonNull ApiResponse<List<Vendor>> getVendors() {
    log.debug("Get vendors");
    return new ApiResponse<>(HttpStatus.OK, "Vendors fetched successfully", vendors);
  }
}
