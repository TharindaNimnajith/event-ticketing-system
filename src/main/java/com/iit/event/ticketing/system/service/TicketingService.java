package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.util.ArrayList;
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

  private final List<Thread> vendorThreads = new ArrayList<>();
  private final List<Thread> customerThreads = new ArrayList<>();

  private boolean started = false;

  /**
   * Start simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> startSimulation() {
    if (started) {
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to start", List.of("Simulation is already running"));
    }

    List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorService.getActiveVendorCount(), customerService.getCustomerCount());

    if (!errors.isEmpty()) {
      return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to start due to missing prerequisites", errors);
    }

    started = true;

    for (Vendor vendor : vendorService.getActiveVendors()) {
      // Thread thread = new Thread(vendor);
      // thread.start();
      //
      // vendorThreads.add(thread);
    }

    for (Customer customer : customerService.getCustomers()) {
      // Thread thread = new Thread(customer);
      // thread.start();
      //
      // customerThreads.add(thread);
    }

    return new ApiResponse<>(HttpStatus.OK, "Started simulation");
  }

  /**
   * Stop simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> stopSimulation() {
    if (!started) {
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to stop", List.of("Simulation is not running"));
    }

    for (Thread thread : vendorThreads) {
      // thread.stop();
    }

    // vendorThreads.clear();

    for (Thread thread : customerThreads) {
      // thread.stop();
    }

    // customerThreads.clear();

    started = false;

    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
