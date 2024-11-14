package com.iit.system.event_ticketing.configuration;

import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Initializing the Ticketing Configurations
 */
@Component
@RequiredArgsConstructor
public class TicketingConfigurationInitializer implements ApplicationRunner {

  private final TicketingConfiguration ticketingConfiguration;

  /**
   * Prompt the user into entering the configuration values and set them into ticketing configurations
   *
   * @param args ApplicationArguments
   */
  @Override
  public void run(final ApplicationArguments args) {
    Scanner scanner = new Scanner(System.in);

    String promptMaxTicketCapacity = "Enter Maximum Tickets Capacity :";
    int maxTicketsCapacity = validateInput(scanner, promptMaxTicketCapacity);
    ticketingConfiguration.setMaxTicketCapacity(maxTicketsCapacity);

    String promptTotalTickets = "Enter Total Tickets :";
    int totalTickets = validateInput(scanner, promptTotalTickets, maxTicketsCapacity); //method overloading was used
    ticketingConfiguration.setTotalTickets(totalTickets);

    String promptTicketReleaseRate = "Enter Ticket Release Rate:";
    int ticketReleaseRate = validateInput(scanner, promptTicketReleaseRate);
    ticketingConfiguration.setTicketReleaseRate(ticketReleaseRate);

    String promptCustomerRetrievalRate = "Enter Customer Retrieval  Rate:";
    int customerRetrievalRate = validateInput(scanner, promptCustomerRetrievalRate);
    ticketingConfiguration.setCustomerRetrievalRate(customerRetrievalRate);
  }

  /**
   * Validating the values entered through the input
   *
   * @param scanner
   * @param prompt
   * @return returns a valid input
   */
  private int validateInput(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt);

      // Checking if the input entered is a valid integer
      if (scanner.hasNextInt()) {
        int input = scanner.nextInt();

        // Checking if the input is a positive integer
        if (input > 0) {
          return input;
        } else {
          System.out.println("Incorrect Input. Please Enter a value Greater than 0 ");
        }
      } else {
        System.out.println("Incorrect Input. Please Enter an Integer Value");
        scanner.next(); // Consuming the invalid input
      }
    }
  }


  private int validateInput(Scanner scanner, String prompt, int maxTicketsCapacity) {
    int totalTickets = validateInput(scanner, prompt);

    while (totalTickets > maxTicketsCapacity) {
      System.out.println("The Total Ticket count  Available should be less than the Maximum Ticket Capacity:" + maxTicketsCapacity);
      totalTickets = validateInput(scanner, prompt);
    }

    return totalTickets;
  }
}
