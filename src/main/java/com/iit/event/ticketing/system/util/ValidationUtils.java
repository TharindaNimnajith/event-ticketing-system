package com.iit.event.ticketing.system.util;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * Validation Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

  /**
   * Validate prerequisites to start simulation
   *
   * @param vendorCount   Vendor count
   * @param customerCount Customer count
   * @return List of error messages if one or more failures found, otherwise empty list (Not null)
   */
  public static @NonNull List<String> validatePrerequisitesToStartSimulation(final int vendorCount, final int customerCount) {
    List<String> errors = new ArrayList<>();

    if (vendorCount <= 0) {
      errors.add("At least one vendor needs to be added");
    }

    if (customerCount <= 0) {
      errors.add("At least one customer needs to be added");
    }

    return errors;
  }
}
