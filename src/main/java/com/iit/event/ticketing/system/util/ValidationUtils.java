package com.iit.event.ticketing.system.util;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * Validation Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ValidationUtils {

  /**
   * Validate prerequisites to start simulation
   *
   * @param activeVendorCount Active vendor count
   * @param customerCount     Customer count
   * @return List of error messages if one or more failures found, otherwise empty list (Not null)
   */
  public static @NonNull List<String> validatePrerequisitesToStartSimulation(final int activeVendorCount, final int customerCount) {
    log.debug("Validating prerequisites to start simulation - Active vendor count: {}; Customer count: {};",
        activeVendorCount,
        customerCount
    );

    List<String> errors = new ArrayList<>();

    if (activeVendorCount <= 0) {
      log.error("Validation failed - At least one active vendor needs to be added");
      errors.add("At least one active vendor needs to be added");
    }

    if (customerCount <= 0) {
      log.error("Validation failed - At least one customer needs to be added");
      errors.add("At least one customer needs to be added");
    }

    return errors;
  }
}
