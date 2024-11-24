package com.iit.event.ticketing.system.configuration

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
        TicketingConfiguration ticketingConfiguration = TicketingConfiguration.builder()
                .maxTicketCapacity(100)
                .totalTickets(10)
                .ticketReleaseRate(20)
                .customerRetrievalRate(30)
                .build()

        when:
        String result = ticketingConfiguration.toString()

        then:
        result == "Max ticket capacity: 100" +
                "\nTotal tickets: 10" +
                "\nTicket release rate (in seconds): 20" +
                "\nCustomer retrieval rate (in seconds): 30"
    }
}
