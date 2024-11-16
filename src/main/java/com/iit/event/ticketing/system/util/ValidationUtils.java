package com.iit.event.ticketing.system.util;

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * Validation Utils
 */
public class ValidationUtils {

  /**
   * Private constructor to hide the implicit public constructor
   */
  private ValidationUtils() {
    // No instantiation
  }

  /**
   * Validate ticketing configurations
   *
   * @param ticketingConfiguration TicketingConfiguration (Not null)
   * @return String list of errors (Not null)
   */
  public static @NonNull List<String> validateTicketingConfigurations(final @NonNull TicketingConfiguration ticketingConfiguration) {
    List<String> errors = new ArrayList<>();

    return errors;
  }
}
