package com.example

import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
class PathUtilsSpec extends Specification {

  def "null parameter values are not allowed"() {
    when:
    PathUtils.matchAntPattern(null, "some string")
    then:
    thrown(NullPointerException)

    when:
    PathUtils.matchAntPattern("some string", null)
    then:
    thrown(NullPointerException)

    when:
    PathUtils.matchAntPath(null, "some string")
    then:
    thrown(NullPointerException)

    when:
    PathUtils.matchAntPath("some string", null)
    then:
    thrown(NullPointerException)
  }


  def "? character should mean any character"() {
    expect:
    PathUtils.matchAntPattern(text, pattern)
    PathUtils.matchAntPath(text, pattern)

    where:
    pattern | text
    "ab?"   | "abc"
    "ab?"   | "ab1"
    "ab?"   | "ab@"
    "a?b"   | "abb"
    "a?b"   | "a1b"
    "a?b"   | "a@b"
    "?ab"   | "aab"
    "?ab"   | "1ab"
    "?ab"   | "@ab"
  }

  def "? character should mean only one character"() {
    expect:
    !PathUtils.matchAntPattern(text, pattern)
    !PathUtils.matchAntPath(text, pattern)

    where:
    pattern | text
    "ab?"   | "ab"
    "ab?"   | "abcc"
    "a?b"   | "ab"
    "a?b"   | "abbb"
    "?ab"   | "ab"
    "?ab"   | "aaab"
  }

  def "* character should mean any character"() {
    expect:
    PathUtils.matchAntPattern(text, pattern)
    PathUtils.matchAntPath(text, pattern)

    where:
    pattern | text
    "ab*"   | "abc"
    "ab*"   | "ab1"
    "ab*"   | "ab@"
    "a*b"   | "abb"
    "a*b"   | "a1b"
    "a*b"   | "a@b"
    "*ab"   | "aab"
    "*ab"   | "1ab"
    "*ab"   | "@ab"
  }

  def "* character should mean any number of characters"() {
    expect:
    PathUtils.matchAntPattern(text, pattern)
    PathUtils.matchAntPath(text, pattern)

    where:
    pattern | text
    "ab*"   | "ab"
    "ab*"   | "abc"
    "ab*"   | "abcd"
    "ab*"   | "abcd1@^&()hjfsh"
    "a*b"   | "ab"
    "a*b"   | "aab"
    "a*b"   | "aabb"
    "a*b"   | "aa1@;bb"
  }

  def "** character should mean any path"() {
    expect:
    PathUtils.matchAntPath(path, pattern)

    where:
    pattern      | path
    "**/abc"     | "abc"
    "**/abc"     | "abc/abc"
    "**/abc"     | "aaa/bbb/abc"
    "aaa/**/bbb" | "aaa/bbb"
    "aaa/**/bbb" | "aaa/ccc/bbb"
    "aaa/**/bbb" | "aaa/c/c/c/bbb"
  }


  def "should match complex rules"() {
    expect:
    PathUtils.matchAntPath(path, pattern) == expected

    where:
    pattern         | path              | expected
    "a?/**/abc"     | "a1/abc"          | true
    "a/**/abc.???"  | "a/c/c/c/abc.123" | true
    "a?/**/abc.???" | "a/c/abc.123"     | false
  }
}
