package com.example

import spock.lang.Specification
import org.apache.tapestry5.ioc.annotations.SubModule
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * @author Ivan Khalopik
 */
@SubModule(TapestryModule)
class TapestryIntegrationSpec extends Specification {

  @Inject
  @Symbol("test-symbol")
  String testSymbol

  def "test-symbol should be tapestry"() {
    expect:
    testSymbol == "tapestry"
  }
}
