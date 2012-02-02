package com.example

import spock.lang.Specification
import spock.lang.FailsWith
import spock.lang.Ignore
import spock.lang.Unroll
import spock.lang.Timeout

/**
 * @author Ivan Khalopik
 */
class InternalExtensionsSpec extends Specification {

  @FailsWith(NumberFormatException)
  @Unroll("#featureName (#data)")
  def "integer parse method should throw exception for wrong parameters"() {
    Integer.parseInt(data)
    where:
    data << ["Hello, World!!!", "0x245", "1798237199878129387197238"]
  }

  @Ignore
  @Timeout(3)
  def "temporary disabled feature"() {
    setup:
    sleep(20000)
  }
}
