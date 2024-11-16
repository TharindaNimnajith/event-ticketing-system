package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Customer Service
 */
@Service
@Slf4j
public class CustomerService {

  private final List<Customer> customers = new ArrayList<>();

  /**
   * Add customer
   *
   * @param customer Customer (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addCustomer(final @NonNull Customer customer) {
    log.debug("Add customer");

    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();
      customer.setRetrievalInterval(ticketingConfiguration.getCustomerRetrievalRate());
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
      return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch ticketing configurations", List.of(ex.getMessage()));
    }

    customers.add(customer);
    return new ApiResponse<>(HttpStatus.OK, "Customer added successfully");
  }

  /**
   * Get customers
   *
   * @return ApiResponse containing List of Customer objects (Not null)
   */
  public @NonNull ApiResponse<List<Customer>> getCustomers() {
    log.debug("Get customers");
    return new ApiResponse<>(HttpStatus.OK, "Customers fetched successfully", customers);
  }
}
