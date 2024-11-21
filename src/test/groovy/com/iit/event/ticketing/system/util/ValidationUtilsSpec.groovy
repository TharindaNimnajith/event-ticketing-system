package com.iit.event.ticketing.system.util

import spock.lang.Specification

/**
 * Spock Tests for Validation Utils
 */
class ValidationUtilsSpec extends Specification {

    /**
     * validatePrerequisitesToStartSimulation - Should return an empty list when both active vendor and customer counts are positive
     */
    def "validatePrerequisitesToStartSimulation - Should return an empty list when both active vendor and customer counts are positive"() {
        given:
        int activeVendorCount = 1
        int customerCount = 1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(activeVendorCount, customerCount)

        then:
        errors.isEmpty()
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 1)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 1)"() {
        given:
        int activeVendorCount = 0
        int customerCount = 0

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(activeVendorCount, customerCount)

        then:
        errors.size() == 2
        errors[0] == "At least one active vendor needs to be added"
        errors[1] == "At least one customer needs to be added"
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 2)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 2)"() {
        given:
        int activeVendorCount = 1
        int customerCount = -1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(activeVendorCount, customerCount)

        then:
        errors.size() == 1
        errors[0] == "At least one customer needs to be added"
    }

    /**
     * validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 3)
     */
    def "validatePrerequisitesToStartSimulation - Should return an error list when both active vendor and customer counts are not positive (test case 3)"() {
        given:
        int activeVendorCount = 0
        int customerCount = 1

        when:
        List<String> errors = ValidationUtils.validatePrerequisitesToStartSimulation(activeVendorCount, customerCount)

        then:
        errors.size() == 1
        errors[0] == "At least one active vendor needs to be added"
    }
}
