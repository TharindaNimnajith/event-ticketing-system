package com.iit.event.ticketing.system.util

import com.iit.event.ticketing.system.configuration.ticketing.TicketingConfiguration
import spock.lang.Specification

/**
 * Spock Tests for Validation Utils
 */
class ValidationUtilsSpec extends Specification {

    /**
     * Should return an empty list when maxTicketCapacity > totalTickets
     */
    def "Should return an empty list when maxTicketCapacity > totalTickets"() {
        given:
        TicketingConfiguration ticketingConfiguration = TicketingConfiguration.builder()
                .maxTicketCapacity(100)
                .totalTickets(99)
                .ticketReleaseRate(1000)
                .customerRetrievalRate(2000)
                .build()

        when:
        List<String> errors = ValidationUtils.validateTicketingConfigurations(ticketingConfiguration)

        then:
        errors.isEmpty()
    }

    /**
     * Should return an empty list when maxTicketCapacity == totalTickets
     */
    def "Should return an empty list when maxTicketCapacity == totalTickets"() {
        given:
        TicketingConfiguration ticketingConfiguration = TicketingConfiguration.builder()
                .maxTicketCapacity(100)
                .totalTickets(100)
                .ticketReleaseRate(1000)
                .customerRetrievalRate(2000)
                .build()

        when:
        List<String> errors = ValidationUtils.validateTicketingConfigurations(ticketingConfiguration)

        then:
        errors.isEmpty()
    }

    /**
     * Should return an empty list when maxTicketCapacity < totalTickets
     */
    def "Should return an empty list when maxTicketCapacity < totalTickets"() {
        given:
        TicketingConfiguration ticketingConfiguration = TicketingConfiguration.builder()
                .maxTicketCapacity(100)
                .totalTickets(101)
                .ticketReleaseRate(1000)
                .customerRetrievalRate(2000)
                .build()

        when:
        List<String> errors = ValidationUtils.validateTicketingConfigurations(ticketingConfiguration)

        then:
        errors.size() == 1
        errors[0] == "Total Tickets (101) should not be greater than Max Ticket Capacity (100)"
    }
}
