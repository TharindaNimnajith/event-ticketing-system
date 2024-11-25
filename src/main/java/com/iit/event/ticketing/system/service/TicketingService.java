package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Ticketing Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TicketingService {

  @Getter
  @Setter
  private static boolean started = false;

  @NonNull
  private final VendorService vendorService;

  @NonNull
  private final CustomerService customerService;

  /**
   * Start simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> startSimulation() {
    log.debug("Starting simulation");

    // Check if simulation is not running before starting it
    if (started) {
      log.error("Failed to start since the simulation is already running");
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to start", List.of("Simulation is already running"));
    }

    // Validate if the system has one or more customers and active vendors to start simulation
    List<String> errors = ValidationUtils.validateStartSimulation(vendorService.getActiveVendors().size(), customerService.getCustomers().size());

    if (!errors.isEmpty()) {
      log.error("Failed to start due to missing prerequisites - Errors: {};", errors.stream().collect(Collectors.joining(", ", "[", "]")));
      return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to start", errors);
    }

    // Set started flag to true as simulation is running
    setStarted(true);

    // Start vendor threads
    for (Vendor vendor : vendorService.getActiveVendors()) {
      Thread thread = new Thread(vendor);
      thread.start();
      log.trace("Started vendor thread - Id: {};", vendor.getId());
    }

    // Start customer threads
    for (Customer customer : customerService.getCustomers()) {
      Thread thread = new Thread(customer);
      thread.start();
      log.trace("Started customer thread - Id: {};", customer.getId());
    }

    return new ApiResponse<>(HttpStatus.OK, "Started simulation");
  }

  /**
   * Stop simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> stopSimulation() {
    log.debug("Stopping simulation");

    // Check if simulation is running before stopping it
    if (!started) {
      log.error("Failed to stop since the simulation is not running");
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to stop", List.of("Simulation is not running"));
    }

    // Stop vendor threads
    for (Vendor vendor : vendorService.getActiveVendors()) {
      vendor.stop();
      log.trace("Stopped vendor thread - Id: {};", vendor.getId());
    }

    // Start customer threads
    for (Customer customer : customerService.getCustomers()) {
      customer.stop();
      log.trace("Stopped customer thread - Id: {};", customer.getId());
    }

    // Set started flag to false as simulation is not running
    setStarted(false);

    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
