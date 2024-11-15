package com.iit.event.ticketing.system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * File Utils
 */
@Slf4j
public class FileUtils {

  private static final String TICKETING_CONFIG_FILE_PATH = "ticketing-configurations.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Private constructor to hide the implicit public constructor
   */
  private FileUtils() {
    // No instantiation
  }

  /**
   * Write TicketingConfiguration object to a JSON file
   *
   * @param ticketingConfiguration TicketingConfiguration
   */
  public static void saveTicketingConfigurationsToFile(final TicketingConfiguration ticketingConfiguration) {
    try {
      ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
      writer.writeValue(new File(TICKETING_CONFIG_FILE_PATH), ticketingConfiguration);
      log.debug("Ticketing configurations saved to file ({})", TICKETING_CONFIG_FILE_PATH);
    } catch (IOException ex) {
      log.error("Error while saving ticketing configurations to file ({})", TICKETING_CONFIG_FILE_PATH);
    }
  }

  /**
   * Read JSON file into TicketingConfiguration object
   *
   * @return TicketingConfiguration (Not null)
   */
  public static @NonNull TicketingConfiguration loadTicketingConfigurationsFromFile() {
    try {
      TicketingConfiguration ticketingConfiguration = objectMapper.readValue(new File(TICKETING_CONFIG_FILE_PATH), TicketingConfiguration.class);
      log.debug("Ticketing configurations loaded from file ({})", TICKETING_CONFIG_FILE_PATH);
      return ticketingConfiguration;
    } catch (IOException ex) {
      log.error("Error while loading ticketing configurations from file ({})", TICKETING_CONFIG_FILE_PATH);
      return new TicketingConfiguration();
    }
  }
}
