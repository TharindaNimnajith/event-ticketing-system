package com.iit.event.ticketing.system.service.ticketing.configurations

import spock.lang.Specification

/**
 * Spock Tests for TicketingConfiguration
 */
class TicketingConfigurationSpec extends Specification {

    /**
     * toString - Should return a String representation of ticketing configurations
     */
    def "toString - Should return a String representation of ticketing configurations"() {
        given:
        TicketingConfiguration ticketingConfiguration = new TicketingConfiguration()
        ticketingConfiguration.setMaxTicketCapacity(100)
        ticketingConfiguration.setTotalTickets(10)
        ticketingConfiguration.setTicketReleaseRate(20)
        ticketingConfiguration.setCustomerRetrievalRate(30)

        when:
        String result = ticketingConfiguration.toString()

        then:
        result == "Max ticket capacity: 100" +
                "\nTotal tickets: 10" +
                "\nTicket release rate (in seconds): 20" +
                "\nCustomer retrieval rate (in seconds): 30"
    }
}
