package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.configuration.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.ApiResponse;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Initialize TicketingConfiguration by prompting user to input values at application startup
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TicketingConfigurationInitializer implements ApplicationRunner {

  @NonNull
  private static final String DEFAULT_VENDOR_ID = "default_vendor";

  private static final int DEFAULT_VENDOR_TICKETS_PER_RELEASE = 1;

  @NonNull
  private final TicketingConfiguration ticketingConfiguration;

  @NonNull
  private final VendorService vendorService;

  @NonNull
  private final TicketPool ticketPool;

  /**
   * Executes on application startup to prompt user for ticketing configuration values
   *
   * @param applicationArguments Incoming application arguments
   */
  @Override
  public void run(final ApplicationArguments applicationArguments) {
    Scanner scanner = new Scanner(System.in);

    // Get user inputs for ticketing configurations
    log.info("\nEnter Ticketing Configurations:");

    // Max ticket capacity
    String promptMaxTicketCapacity = "Max Ticket Capacity:";
    int maxTicketCapacity = validateInput(scanner, promptMaxTicketCapacity);
    ticketingConfiguration.setMaxTicketCapacity(maxTicketCapacity);

    // Total tickets
    String promptTotalTickets = "Total Tickets:";
    int totalTickets = validateInput(scanner, promptTotalTickets, maxTicketCapacity);
    ticketingConfiguration.setTotalTickets(totalTickets);

    // Ticket release rate
    String promptTicketReleaseRate = "Ticket Release Rate (In Seconds):";
    int ticketReleaseRate = validateInput(scanner, promptTicketReleaseRate);
    ticketingConfiguration.setTicketReleaseRate(ticketReleaseRate);

    // Customer retrieval rate
    String promptCustomerRetrievalRate = "Customer Retrieval Rate (In Seconds):";
    int customerRetrievalRate = validateInput(scanner, promptCustomerRetrievalRate);
    ticketingConfiguration.setCustomerRetrievalRate(customerRetrievalRate);

    // Add total tickets to ticket pool
    addTotalTicketsToTicketPool(ticketingConfiguration.getTotalTickets());

    log.info("\nTicketing configurations:\n{};", ticketingConfiguration);

    // Save ticketing configurations to file
    try {
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations - File path: {}; Error: {};",
          TICKETING_CONFIGURATIONS_FILE_PATH,
          ex.getMessage(),
          ex
      );
    }
  }

  /**
   * <p> Validate to ensure that total tickets user input is a non-negative integer not greater than max ticket capacity </p>
   * <p> Otherwise, prompt again with a relevant error message </p>
   *
   * @param scanner           Scanner (Not null)
   * @param prompt            Prompt (Not null)
   * @param maxTicketCapacity Max ticket capacity
   * @return Valid total tickets input
   */
  private int validateInput(final @NonNull Scanner scanner, final @NonNull String prompt, final int maxTicketCapacity) {
    int totalTickets;

    while (true) {
      log.info("\n{}", prompt);

      // Check if user input is an integer
      if (scanner.hasNextInt()) {
        int input = scanner.nextInt();

        // Check if user input is a non-negative integer
        if (input >= 0) {
          totalTickets = input;
          break;
        } else {
          log.warn("\nInvalid input (Value should be non-negative) - Input: {};", input);
        }
      } else {
        // Consuming invalid input
        log.warn("\nInvalid input (Value should be an integer) - Input: {};", scanner.next());
      }
    }

    // Check if total tickets user input is not greater than max ticket capacity
    if (totalTickets > maxTicketCapacity) {
      log.warn("\nInvalid input (Total tickets should not be greater than max ticket capacity) - Total tickets: {}; Max ticket capacity: {};",
          totalTickets,
          maxTicketCapacity
      );

      totalTickets = validateInput(scanner, prompt, maxTicketCapacity);
    }

    return totalTickets;
  }

  /**
   * <p> Validate to ensure that user input is a positive integer </p>
   * <p> Otherwise prompt again with a relevant error message </p>
   *
   * @param scanner Scanner (Not null)
   * @param prompt  Prompt (Not null)
   * @return Valid input
   */
  private int validateInput(final @NonNull Scanner scanner, final @NonNull String prompt) {
    while (true) {
      log.info("\n{}", prompt);

      // Check if user input is an integer
      if (scanner.hasNextInt()) {
        int input = scanner.nextInt();

        // Check if user input is a positive integer
        if (input > 0) {
          return input;
        } else {
          log.warn("\nInvalid input (Value should be greater than 0) - Input: {};", input);
        }
      } else {
        // Consuming invalid input
        log.warn("\nInvalid input (Value should be an integer) - Input: {};", scanner.next());
      }
    }
  }

  /**
   * Add total tickets to ticket pool
   *
   * @param totalTickets Total tickets
   */
  private void addTotalTicketsToTicketPool(final int totalTickets) {
    log.debug("Adding initial set of tickets to ticket pool - Total tickets: {};", totalTickets);

    // No need to add default vendor or tickets to the ticket pool if there are no tickets
    if (totalTickets <= 0) {
      log.debug("No tickets added to ticket pool since total tickets input is not positive");
      return;
    }

    // Add default vendor
    Vendor vendor = new Vendor(DEFAULT_VENDOR_ID, DEFAULT_VENDOR_TICKETS_PER_RELEASE);
    ApiResponse<Object> apiResponse = vendorService.addVendor(vendor);

    // Handle failure cases when adding default vendor
    if (!apiResponse.getHttpStatus().is2xxSuccessful()) {
      log.error("Error adding default vendor to ticket pool - HTTP Status: {}; Message: {};\nErrors: {};",
          apiResponse.getHttpStatus(),
          apiResponse.getMessage(),
          apiResponse.getErrors() != null ? apiResponse.getErrors().stream().collect(Collectors.joining(", ", "[", "]")) : null
      );

      return;
    }

    // Add total tickets with default vendor
    for (int i = 0; i < totalTickets; i++) {
      ticketPool.addTickets(vendor.getId(), vendor.getTicketsPerRelease());
    }

    // Deactivate default vendor
    vendorService.removeVendor(vendor);
  }
}
