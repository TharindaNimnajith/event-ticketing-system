package com.iit.event.ticketing.system.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.annotation.Configuration;

/**
 * Ticketing Configuration
 */
@Configuration
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
public class TicketingConfiguration implements Serializable {

  @JsonProperty("max_ticket_capacity")
  private int maxTicketCapacity;

  @JsonProperty("total_tickets")
  private int totalTickets;

  @JsonProperty("ticket_release_rate")
  private int ticketReleaseRate;

  @JsonProperty("customer_retrieval_rate")
  private int customerRetrievalRate;

  /**
   * Override the default toString method to return customized String representation of ticketing configurations
   *
   * @return String representation of ticketing configurations
   */
  @Override
  public String toString() {
    return new ToStringBuilder(this, new CustomToStringStyle())
        .append("Max ticket capacity", maxTicketCapacity)
        .append("Total tickets", totalTickets)
        .append("Ticket release rate (in seconds)", ticketReleaseRate)
        .append("Customer retrieval rate (in seconds)", customerRetrievalRate)
        .toString();
  }

  /**
   * Custom ToStringStyle
   */
  private static class CustomToStringStyle extends ToStringStyle {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Construct a ToStringStyle object with custom configurations
     */
    public CustomToStringStyle() {
      this.setUseClassName(false);
      this.setUseIdentityHashCode(false);
      this.setFieldNameValueSeparator(": ");
      this.setFieldSeparator("\n");
      this.setContentStart("");
      this.setContentEnd("");
      this.setNullText("");
    }
  }
}
