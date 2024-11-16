package com.iit.event.ticketing.system.api.v1;

import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.service.VendorService;
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
 * Vendor Controller
 */
@RestController
@RequestMapping("v1/vendors")
@RequiredArgsConstructor
@Slf4j
public class VendorController {

  private final VendorService vendorService;

  /**
   * Add vendor
   *
   * @param vendor Vendor (Not null)
   * @return ResponseEntity containing ApiResponse (Not null)
   */
  @PostMapping
  public @NonNull ResponseEntity<ApiResponse<Object>> addVendor(final @RequestBody @Valid @NonNull Vendor vendor) {
    log.info("Add vendor");
    ApiResponse<Object> apiResponse = vendorService.addVendor(vendor);
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }

  /**
   * Get vendor
   *
   * @return ResponseEntity containing ApiResponse with List of Vendor objects (Not null)
   */
  @GetMapping
  public @NonNull ResponseEntity<ApiResponse<List<Vendor>>> getVendor() {
    log.info("Get vendors");
    ApiResponse<List<Vendor>> apiResponse = vendorService.getVendors();
    return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
  }
}
