package com.example

import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
class LocaleUtilsSpec extends Specification {

    def "Locale parser should ignore null values"() {
        expect:
        LocaleUtils.parseLocale(null) != null
    }
}
