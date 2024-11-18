package com.iit.event.ticketing.system.util

import com.iit.event.ticketing.system.core.model.entity.TicketingConfiguration
import spock.lang.Specification

/**
 * Spock Tests for Validation Utils
 */
class ValidationUtilsSpec extends Specification {

    /**
     * validateTicketingConfigurations - Should return an empty list when maxTicketCapacity > totalTickets
     */
    def "validateTicketingConfigurations - Should return an empty list when maxTicketCapacity > totalTickets"() {
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
     * validateTicketingConfigurations - Should return an empty list when maxTicketCapacity == totalTickets
     */
    def "validateTicketingConfigurations - Should return an empty list when maxTicketCapacity == totalTickets"() {
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
     * validateTicketingConfigurations - Should return an empty list when maxTicketCapacity < totalTickets
     */
    def "validateTicketingConfigurations - Should return an error list when maxTicketCapacity < totalTickets"() {
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

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 1)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 1)"() {
        given:
        int vendorCount = 0
        int customerCount = 0

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorCount, customerCount)

        then:
        errors.size() == 2
        errors[0] == "At least one vendor needs to be added"
        errors[1] == "At least one customer needs to be added"
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 2)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 2)"() {
        given:
        int vendorCount = 1
        int customerCount = -1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorCount, customerCount)

        then:
        errors.size() == 1
        errors[0] == "At least one customer needs to be added"
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 3)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both vendor and customer counts are not positive (test case 3)"() {
        given:
        int vendorCount = 0
        int customerCount = 1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorCount, customerCount)

        then:
        errors.size() == 1
        errors[0] == "At least one vendor needs to be added"
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an empty list when both vendor and customer counts are positive
     */
    def "validatePrerequisitesToStartSimulation - Should return an empty list when both vendor and customer counts are positive"() {
        given:
        int vendorCount = 1
        int customerCount = 1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(vendorCount, customerCount)

        then:
        errors.isEmpty()
    }
}
