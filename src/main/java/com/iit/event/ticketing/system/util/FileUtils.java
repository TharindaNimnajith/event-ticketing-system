package com.iit.event.ticketing.system.util;

import static com.iit.event.ticketing.system.core.CommonConstants.TICKETING_CONFIGURATIONS_FILE_PATH;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.iit.event.ticketing.system.core.model.TicketingConfiguration;
import java.io.File;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * File Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FileUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Write TicketingConfiguration object to a JSON file
   *
   * @param ticketingConfiguration TicketingConfiguration (Not null)
   * @throws IOException IOException
   */
  public static void saveTicketingConfigurationsToFile(final @NonNull TicketingConfiguration ticketingConfiguration) throws IOException {
    ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
    writer.writeValue(new File(TICKETING_CONFIGURATIONS_FILE_PATH), ticketingConfiguration);
    log.debug("Ticketing configurations saved to file ({})", TICKETING_CONFIGURATIONS_FILE_PATH);
  }

  /**
   * Read JSON file into TicketingConfiguration object
   *
   * @return TicketingConfiguration (Not null)
   * @throws IOException IOException
   */
  public static @NonNull TicketingConfiguration loadTicketingConfigurationsFromFile() throws IOException {
    TicketingConfiguration ticketingConfiguration = objectMapper.readValue(new File(TICKETING_CONFIGURATIONS_FILE_PATH), TicketingConfiguration.class);
    log.debug("Ticketing configurations loaded from file ({})", TICKETING_CONFIGURATIONS_FILE_PATH);
    return ticketingConfiguration;
  }
}
