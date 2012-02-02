package com.example

import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
class PathSearcherSpec extends Specification {

  def "it should search files under the file system"() {
    given:
    def searcher = PathSearcher.create(inClasspath("test1"))
    when:
    def results = searcher.search();
    then:
    results.containsAll(["1.txt", "2.txt"]);
    results.size() == 2
  }

  def "it should search files under the file system in child directories"() {
    given:
    def searcher = PathSearcher.create(inClasspath("test2"))
    when:
    def results = searcher.search();
    then:
    results.containsAll(["1.txt", "child/2.txt", "child/3.txt", "child/child/4.txt"]);
    results.size() == 4
  }

  def "it should search files in archives"() {
    given:
    def jarSearcher = PathSearcher.create(inJar("test0.jar", "META-INF/services"))
    def zipSearcher = PathSearcher.create(inJar("test1.zip", "META-INF/services"))
    when:
    def jarResults = jarSearcher.search();
    def zipResults = zipSearcher.search();
    then:
    jarResults.containsAll(["com.example.Service", "com.example.AnotherService"]);
    jarResults.size() == 2
    zipResults.containsAll(["com.example.Service"]);
    zipResults.size() == 1
  }

  def "it should search files in archives in child directories"() {
    given:
    def jarSearcher = PathSearcher.create(inJar("test0.jar", "META-INF/"))
    def zipSearcher = PathSearcher.create(inJar("test1.zip", "META-INF/"))
    when:
    def jarResults = jarSearcher.search();
    def zipResults = zipSearcher.search();
    then:
    jarResults.containsAll([
        "services/com.example.Service",
        "services/com.example.AnotherService",
        "maven/com.example/test0/pom.xml",
        "maven/com.example/test0/pom.properties",
        "MANIFEST.MF"
    ]);
    jarResults.size() == 5
    zipResults.containsAll(["services/com.example.Service"]);
    zipResults.size() == 1
  }

  def "it should search files that match at least one of the include patterns"() {
    given:
    def searcher = PathSearcher.create(inClasspath("test3")).include("**/*.xml", "**/child/*.*")
    when:
    def results = searcher.search()
    then:
    results.containsAll(["2.xml", "child/4.txt", "child/5.xml", "child/6.yaml"])
    results.size() == 4
  }

  def "it should not search files that match at least one of the exclude patterns"() {
    given:
    def searcher = PathSearcher.create(inClasspath("test3")).exclude("**/*.xml", "**/child/*.????")
    when:
    def results = searcher.search()
    then:
    results.containsAll(["1.txt", "3.yaml", "child/4.txt"])
    results.size() == 3
  }

  def "it should not search files that match both include and exclude patterns"() {
    given:
    def searcher = PathSearcher.create(inClasspath("test3")).include("**/*.xml").exclude("**/child/*")
    when:
    def results = searcher.search()
    then:
    results.containsAll(["2.xml"])
    results.size() == 1
  }

  private String inClasspath(path) {
    return ClassLoader.getSystemResource(path).toExternalForm()
  }

  private String inArchive(archiveType, archivePath, innerPath) {
    return archiveType + ":" + inClasspath(archivePath) + "!/" + innerPath;
  }

  private String inJar(filePath, innerPath) {
    return inArchive("jar", filePath, innerPath)
  }
}
