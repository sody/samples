package com.example

import spock.lang.*

/**
 * @author Ivan Khalopik
 */
class CustomExtensionsSpec extends Specification {

  @Repeat(10)
  def "custom extension"() {
    expect:
    Integer.parseInt("123") == 123
  }
}
