package com.example

import com.google.inject.Inject
import com.google.inject.name.Named
import spock.guice.UseModules
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
@UseModules(GuiceModule)
class GuiceIntegrationSpec extends Specification {

  @Inject
  @Named("test-symbol")
  String testSymbol

  def "test-symbol should be guice"() {
    expect:
    testSymbol == "guice"
  }
}
