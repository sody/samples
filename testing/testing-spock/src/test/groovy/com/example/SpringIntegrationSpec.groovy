package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
@ContextConfiguration(locations = "context.xml")
class SpringIntegrationSpec extends Specification {

  @Autowired
  String testSymbol

  def "test-symbol should be spring"() {
    expect:
    testSymbol == "spring"
  }
}
