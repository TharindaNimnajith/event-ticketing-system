package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Customer;
import com.iit.event.ticketing.system.service.CustomerService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer Controller
 */
@RestController
@RequestMapping("v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

  @NonNull
  private final CustomerService customerService;

  /**
   * Add customer
   *
   * @param customer Customer (Not null)
   * @return ResponseEntity containing ApiResponse (Not null)
   */
  @PostMapping
  public @NonNull ResponseEntity<ApiResponse<Object>> addCustomer(final @RequestBody @Valid @NonNull Customer customer) {
    log.info("Add customer - Id: {};", customer.getId());
    ApiResponse<Object> apiResponse = customerService.addCustomer(customer);
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

  /**
   * Get customers list
   *
   * @return ResponseEntity containing ApiResponse with List of Customer objects (Not null)
   */
  @GetMapping
  public @NonNull ResponseEntity<ApiResponse<List<Customer>>> getCustomersList() {
    log.info("Get customers list");
    ApiResponse<List<Customer>> apiResponse = customerService.getCustomersList();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
