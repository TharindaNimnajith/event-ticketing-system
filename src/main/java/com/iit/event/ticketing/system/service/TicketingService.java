package com.iit.event.ticketing.system.service;

import brave.propagation.CurrentTraceContext;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.util.ArrayList;
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

  @NonNull
  private final CurrentTraceContext currentTraceContext;

  private List<Thread> vendorThreads;
  private List<Thread> customerThreads;

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
    List<String> errors = ValidationUtils.validateStartSimulation(
        vendorService.getActiveVendors().size(),
        customerService.getCustomers().size()
    );

    if (!errors.isEmpty()) {
      log.error("Failed to start due to missing prerequisites - Errors: {};", errors.stream().collect(Collectors.joining(", ", "[", "]")));
      return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to start", errors);
    }

    // Initialize vendor and customer thread lists
    vendorThreads = new ArrayList<>();
    customerThreads = new ArrayList<>();

    // Set started flag to true as simulation is running
    setStarted(true);

    // Create vendor threads and add to vendor threads list
    for (Vendor vendor : vendorService.getActiveVendors()) {
      Thread thread = new Thread(currentTraceContext.wrap(vendor));
      thread.setName(vendor.getId());
      vendorThreads.add(thread);
    }

    log.trace("Created vendor threads - Thread count: {};", vendorThreads.size());

    // Create customer threads and add to customer threads list
    for (Customer customer : customerService.getCustomers()) {
      Thread thread = new Thread(currentTraceContext.wrap(customer));
      thread.setName(customer.getId());
      customerThreads.add(thread);
    }

    log.trace("Created customer threads - Thread count: {};", customerThreads.size());

    // Start vendor threads
    for (Thread vendorThread : vendorThreads) {
      vendorThread.start();
      log.trace("Started vendor thread - Id: {};", vendorThread.getName());
    }

    // Start customer threads
    for (Thread customerThread : customerThreads) {
      customerThread.start();
      log.trace("Started customer thread - Id: {};", customerThread.getName());
    }

    log.debug("Started simulation");

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

    // Stop customer threads
    for (Customer customer : customerService.getCustomers()) {
      customer.stop();
      log.trace("Stopped customer thread - Id: {};", customer.getId());
    }

    // Join vendor threads
    for (Thread vendorThread : vendorThreads) {
      try {
        vendorThread.join();
        log.trace("Joined vendor thread - Id: {};", vendorThread.getName());
      } catch (InterruptedException ex) {
        log.error("Error joining vendor thread - Id: {}; Error: {};", vendorThread.getName(), ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }

    // Join customer threads
    for (Thread customerThread : customerThreads) {
      try {
        customerThread.join();
        log.trace("Joined customer thread - Id: {};", customerThread.getName());
      } catch (InterruptedException ex) {
        log.error("Error joining customer thread - Id: {}; Error: {};", customerThread.getName(), ex.getMessage(), ex);
        Thread.currentThread().interrupt();
      }
    }

    // Clear thread lists after joining
    vendorThreads.clear();
    customerThreads.clear();

    // Set started flag to false as simulation is not running
    setStarted(false);

    log.debug("Stopped simulation");

    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
