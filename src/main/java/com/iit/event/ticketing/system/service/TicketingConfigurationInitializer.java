package com.iit.event.ticketing.system.service;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration;
import com.iit.event.ticketing.system.core.model.entity.Vendor;
import com.iit.event.ticketing.system.util.FileUtils;
import java.io.IOException;
import java.util.Scanner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Initialize TicketingConfiguration by prompting user to input values at application startup
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TicketingConfigurationInitializer implements ApplicationRunner {

  private static final String DEFAULT_VENDOR_NAME = "default_vendor";
  private static final int DEFAULT_VENDOR_TICKETS_PER_RELEASE = 1;

  private final VendorService vendorService;
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

    String promptMaxTicketCapacity = "Max Ticket Capacity:";
    int maxTicketCapacity = validateInput(scanner, promptMaxTicketCapacity);

    String promptTotalTickets = "Total Tickets:";
    int totalTickets = validateInput(scanner, promptTotalTickets, maxTicketCapacity);

    String promptTicketReleaseRate = "Ticket Release Rate (In Seconds):";
    int ticketReleaseRate = validateInput(scanner, promptTicketReleaseRate);

    String promptCustomerRetrievalRate = "Customer Retrieval Rate (In Seconds):";
    int customerRetrievalRate = validateInput(scanner, promptCustomerRetrievalRate);

    // Save ticketing configurations to file
    TicketingConfiguration ticketingConfiguration = new TicketingConfiguration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

    try {
      FileUtils.saveTicketingConfigurationsToFile(ticketingConfiguration);
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations to file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }

    // Print loaded ticketing configurations from file to console
    printTicketingConfigurations();

    // Add total tickets to ticket pool
    addTotalTicketsToTicketPool(totalTickets);
  }

  /**
   * <p> Validate to ensure that Total Tickets user input is a non-negative integer not greater than Max Ticket Capacity </p>
   * <p> Otherwise prompt again with a relevant error message </p>
   *
   * @param scanner           Scanner (Not null)
   * @param prompt            Prompt (Not null)
   * @param maxTicketCapacity Max Ticket Capacity
   * @return Valid Total Tickets input
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
          log.warn("\nIncorrect Input ({}) - Value should be non-negative", input);
        }
      } else {
        // Consuming invalid input
        log.warn("\nIncorrect Input ({}) - Value should be an integer", scanner.next());
      }
    }

    // Check if Total Tickets user input is not greater than Max Ticket Capacity
    while (totalTickets > maxTicketCapacity) {
      log.warn("\nIncorrect Input - Total Tickets ({}) should not be greater than Max Ticket Capacity ({})", totalTickets, maxTicketCapacity);
      totalTickets = validateInput(scanner, prompt);
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
          log.warn("\nIncorrect Input ({}) - Value should be greater than 0", input);
        }
      } else {
        // Consuming invalid input
        log.warn("\nIncorrect Input ({}) - Value should be an integer", scanner.next());
      }
    }
  }

  /**
   * Print ticketing configurations loaded from file
   */
  private void printTicketingConfigurations() {
    try {
      TicketingConfiguration ticketingConfiguration = FileUtils.loadTicketingConfigurationsFromFile();

      log.info("\nTicketing Configurations:\n{}: {}\n{}: {}\n{}: {}\n{}: {}",
          "Max Ticket Capacity", ticketingConfiguration.getMaxTicketCapacity(),
          "Total Tickets", ticketingConfiguration.getTotalTickets(),
          "Ticket Release Rate (In Seconds)", ticketingConfiguration.getTicketReleaseRate(),
          "Customer Retrieval Rate (In Seconds)", ticketingConfiguration.getCustomerRetrievalRate()
      );
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }
  }

  /**
   * Add total tickets to ticket pool
   *
   * @param totalTickets Total tickets
   */
  private void addTotalTicketsToTicketPool(final int totalTickets) {
    try {
      Vendor vendor = new Vendor(DEFAULT_VENDOR_NAME, DEFAULT_VENDOR_TICKETS_PER_RELEASE);
      vendorService.addVendor(vendor);

      for (int i = 0; i < totalTickets; i++) {
        ticketPool.addTickets(vendor.getId(), vendor.getTicketsPerRelease());
      }
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({}) - Error: {}", TICKETING_CONFIGURATIONS_FILE_PATH, ex.getMessage(), ex);
    }
  }
}
