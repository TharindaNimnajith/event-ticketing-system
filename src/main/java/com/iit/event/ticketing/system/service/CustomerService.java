package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.repository.CustomerRepository;
import com.iit.event.ticketing.system.service.ticket.pool.TicketPool;
import com.iit.event.ticketing.system.service.ticketing.configurations.TicketingConfiguration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Customer Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

  @NonNull
  private final TicketingConfiguration ticketingConfiguration;

  @NonNull
  private final TicketPool ticketPool;

  @NonNull
  private final CustomerRepository customerRepository;

  @NonNull
  @Getter
  private final List<Customer> customers = new ArrayList<>();

  /**
   * Add customer
   *
   * @param customer Customer (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addCustomer(final @NonNull Customer customer) {
    log.debug("Adding customer - Id: {};", customer.getId());

    // Check if simulation is not running before adding the new customer
    if (TicketingService.isStarted()) {
      log.error("Failed to add customer since the simulation is currently running - Id: {};", customer.getId());
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to add customer", List.of("Simulation is currently running"));
    }

    // Set retrieval interval ticketing configuration and ticket pool
    customer.setRetrievalInterval(ticketingConfiguration.getCustomerRetrievalRate());
    customer.setTicketPool(ticketPool);

    // Add customer to customers list and save in database
    customers.add(customer);
    customerRepository.save(customer);

    log.trace("Added customer - Id: {};", customer.getId());

    return new ApiResponse<>(HttpStatus.CREATED, "Customer added successfully");
  }

  /**
   * Get customers list
   *
   * @return ApiResponse containing List of Customer objects (Not null)
   */
  public @NonNull ApiResponse<List<Customer>> getCustomersList() {
    log.debug("Fetching customers list");
    return new ApiResponse<>(HttpStatus.OK, "Customers fetched successfully", customerRepository.findAll());
  }
}
