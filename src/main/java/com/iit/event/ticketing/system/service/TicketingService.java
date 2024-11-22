package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.FileUtils;
import com.iit.event.ticketing.system.util.ValidationUtils;
import java.io.IOException;
import java.util.List;
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

  private final VendorService vendorService;
  private final CustomerService customerService;
  private final TicketPool ticketPool;

  /**
   * Start simulation
   *
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> startSimulation() {
    if (started) {
      log.debug("Failed to start since the simulation is already running");
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to start", List.of("Simulation is already running"));
    }

    List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorService.getActiveVendorCount(), customerService.getCustomerCount());

    if (!errors.isEmpty()) {
      log.debug("Failed to start due to missing prerequisites - {}", String.join(", ", errors));
      return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, "Failed to start due to missing prerequisites", errors);
    }

    setStarted(true);

    for (Vendor vendor : vendorService.getActiveVendors()) {
      Thread thread = new Thread(vendor);
      thread.start();
    }

    for (Customer customer : customerService.getCustomers()) {
      Thread thread = new Thread(customer);
      thread.start();
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
      log.debug("Failed to stop since the simulation is not running");
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to stop", List.of("Simulation is not running"));
    }

    for (Vendor vendor : vendorService.getActiveVendors()) {
      vendor.stop();
    }

    for (Customer customer : customerService.getCustomers()) {
      customer.stop();
    }

    setStarted(false);

    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
      ticketingConfiguration.setTotalTickets(ticketPool.getAvailableTicketCount());
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while loading or saving ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }

    return new ApiResponse<>(HttpStatus.OK, "Stopped simulation");
  }
}
