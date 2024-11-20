package com.iit.event.ticketing.system.service;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * Customer Service
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

  private final TicketPool ticketPool;
  private final TicketingService ticketingService;

  @Getter
  private final List<Customer> customers = new ArrayList<>();

  /**
   * Add customer
   *
   * @param customer Customer (Not null)
   * @return ApiResponse (Not null)
   */
  public @NonNull ApiResponse<Object> addCustomer(final @NonNull Customer customer) {
    if (ticketingService.isStarted()) {
      return new ApiResponse<>(HttpStatus.CONFLICT, "Failed to add customer", List.of("Simulation is currently running"));
    }

    customer.setTicketPool(ticketPool);
    customers.add(customer);
    return new ApiResponse<>(HttpStatus.OK, "Customer added successfully");
  }

  /**
   * Get customers list
   *
   * @return ApiResponse containing List of Customer objects (Not null)
   */
  public @NonNull ApiResponse<List<Customer>> getCustomersList() {
    return new ApiResponse<>(HttpStatus.OK, "Customers fetched successfully", customers);
  }

  /**
   * Get customer count
   *
   * @return Customer count
   */
  public int getCustomerCount() {
    return customers.size();
  }
}
