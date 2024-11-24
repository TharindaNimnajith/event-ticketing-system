package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.configuration.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.repository.CustomerRepository;
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
    log.debug("Adding customer - Id: {}; Name: {};", customer.getId(), customer.getName());

    if (TicketingService.isStarted()) {
      log.debug("Failed to add customer since the simulation is currently running - Id: {}; Name: {};", customer.getId(), customer.getName());
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to add customer", List.of("Simulation is currently running"));
    }

    customer.setRetrievalInterval(ticketingConfiguration.getCustomerRetrievalRate());
    customer.setTicketPool(ticketPool);

    customers.add(customer);
    customerRepository.save(customer);

    return new ApiResponse<>(HttpStatus.OK, "Customer added successfully");
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

  /**
   * Get customer count
   *
   * @return Customer count
   */
  public int getCustomerCount() {
    log.debug("Get customer count");
    return customers.size();
  }
}
