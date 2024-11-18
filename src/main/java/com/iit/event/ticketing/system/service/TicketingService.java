package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Service
 */
@Service
@RequiredArgsConstructor
public class TicketingService {

  private final VendorService vendorService;
  private final CustomerService customerService;

  /**
   * Start simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> start() {
    List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorService.getVendorCount(), customerService.getCustomerCount());

    if (!errors.isEmpty()) {
      return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to process the request due to missing prerequisites", errors);
    }

    return new ApiResponse<>(HttpStatus.OK, "Started simulation");
  }

  /**
   * Stop simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> stop() {
    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
