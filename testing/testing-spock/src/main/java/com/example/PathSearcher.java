package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class represents utility for search resources under specified path with applying ant include and exclude patterns.
 * Path can point to path inside zip file. Some examples of path: <br/>
 * {@code file:/C:/some/location} <br/>
 * {@code file:/C:/some/location/some_file.ext} <br/>
 * {@code C:/some/location/} <br/>
 * {@code zip:file:/C:/some/location/zipFile.zip!/some/folder} <br/>
 * {@code jar:file:/C:/some/location/jarFile.jar!/some/folder/some_file.ext} <br/>
 * {@code jar:file:/C:/some/location/jarFile.jar!/}
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PathSearcher {
  private static final Pattern ZIP_FILE_PATTERN = Pattern.compile("(zip|jar):file:(.*)!/([^!]*)");

  private final Set<String> includes = new HashSet<String>();
  private final Set<String> excludes = new HashSet<String>();

  private final String path;

  private final Set<String> resources = new HashSet<String>();

  /**
   * Creates new instance of path searcher utility that will execute search under specified path. The path can point to path inside
   * zip file.
   *
   * @param path path where to search child resources, not {@code null}
   * @return path searcher utility instance, not {@code null}
   */
  public static PathSearcher create(final String path) {
    return new PathSearcher(path);
  }

  /**
   * Creates new path searcher instance that will execute search under specified path. This constructor is private, use {@link
   * #create(String)} static method instead.
   *
   * @param path path where to search child resources, not {@code null}
   */
  private PathSearcher(final String path) {
    this.path = path;
  }

  /**
   * Adds include patterns to search configuration. The pattern may contain some special characters: <br/>
   * {@code '**'} means any path <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character
   *
   * @param patterns include patterns
   * @return this path searcher instance
   */
  public PathSearcher include(final String... patterns) {
    includes.addAll(Arrays.asList(patterns));
    return this;
  }

  /**
   * Adds include patterns to search configuration. The pattern may contain some special characters: <br/>
   * {@code '**'} means any path <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character
   *
   * @param patterns collection of include patterns
   * @return this path searcher instance
   */
  public PathSearcher include(final Collection<String> patterns) {
    if (patterns != null) {
      includes.addAll(patterns);
    }
    return this;
  }

  /**
   * Adds exclude patterns to search configuration. The pattern may contain some special characters: <br/>
   * {@code '**'} means any path <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character
   *
   * @param patterns exclude patterns
   * @return this path searcher instance
   */
  public PathSearcher exclude(final String... patterns) {
    excludes.addAll(Arrays.asList(patterns));
    return this;
  }

  /**
   * Adds exclude patterns to search configuration. The pattern may contain some special characters: <br/>
   * {@code '**'} means any path <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character
   *
   * @param patterns collection of exclude patterns
   * @return this path searcher instance
   */
  public PathSearcher exclude(final Collection<String> patterns) {
    if (patterns != null) {
      excludes.addAll(patterns);
    }
    return this;
  }

  /**
   * Looks up for all resources under given path which must be matched against include and exclude patterns. If there are no
   * patterns specified, all resources found under that path will be returned.
   *
   * @return a set of all found resources which match include and exclude patterns or empty set if nothing found
   */
  public Set<String> search() {
    resources.clear();
    try {
      final Matcher matcher = ZIP_FILE_PATTERN.matcher(path);
      if (matcher.matches()) {
        final String fileName = matcher.group(2);
        final String directory = matcher.group(3);
        final File file = new File(fileName);
        if (file.exists()) {
          findResourcesInZip(file, directory);
        }
      } else {
        final URI pathURI = new URI(path);
        final File file = new File(pathURI.getPath());
        if (file.exists()) {
          findResourcesInPath(file, "");
        }
      }
    } catch (Exception e) {
      //todo: log warning
    }
    return resources;
  }

  /**
   * Looks up for all resources under given path which must be matched against include and exclude patterns. Specified relative
   * path will be added to resulting resource names. If given path is file, it will be added to resulting resources.
   *
   * @param path         directory or file where to search child resources, should exist, not {@code null}
   * @param relativePath relative path that will be added to resulting resource names, not {@code null}
   */
  private void findResourcesInPath(final File path, final String relativePath) {
    if (path.isDirectory()) {
      for (String fileName : path.list()) {
        final File child = new File(path, fileName);
        final String filePath = isEmpty(relativePath) ? fileName :
            relativePath + PathUtils.PATH_SEPARATOR + fileName;

        findResourcesInPath(child, filePath);
      }
    } else if (matchAntPath(relativePath)) {
      resources.add(relativePath);
    }
  }

  /**
   * Looks up for all resources under given zip file with inner path which must be matched against include and exclude patterns .
   * If given path is file, it will be added to resulting resources.
   *
   * @param file         file file where to search child resources, should exist, not {@code null}
   * @param relativePath directory or file where to search child resources, not {@code null}
   * @throws java.io.IOException if error occurs while reading zip file
   */
  private void findResourcesInZip(final File file, final String relativePath) throws IOException {
    final ZipFile zipFile = new ZipFile(file);

    final int startPosition = relativePath.endsWith(PathUtils.PATH_SEPARATOR) || isEmpty(relativePath) ?
        relativePath.length() :
        relativePath.length() + 1;

    final Enumeration<? extends ZipEntry> entries = zipFile.entries();
    while (entries.hasMoreElements()) {
      final ZipEntry entry = entries.nextElement();
      if (!entry.isDirectory()) {
        final String entryName = entry.getName();
        if (entryName.startsWith(relativePath)) {
          if (relativePath.length() == entryName.length()) {
            resources.add("");
            break;
          }
          //todo: rethink it, may cause array index out of bounds exception
          final String filePath = entryName.substring(startPosition);
          if (matchAntPath(filePath)) {
            resources.add(filePath);
          }
        }
      }
    }
    zipFile.close();
  }

  /**
   * Tests whether a given path matches include and exclude patterns. Include and exclude patterns may contain some special
   * characters: <br/> {@code '**'} means any path <br/> {@code '*'} means zero or more characters <br/> {@code '?'} means one and
   * only one character
   *
   * @param path path which must be matched against the patterns, not {@code null}
   * @return {@code true} if path matches against include and exclude patterns, {@code false} otherwise
   */
  private boolean matchAntPath(final String path) {
    return (includes.isEmpty() || matchAntPath(path, includes)) && !matchAntPath(path, excludes);
  }

  /**
   * Tests whether a given path matches filters. Filters may contain some special characters: <br/> {@code '**'} means any path
   * <br/> {@code '*'} means zero or more characters <br/> {@code '?'} means one and only one character
   *
   * @param path    path which must be matched against the patterns, not {@code null}
   * @param filters path filters, not {@code null}
   * @return {@code true} if path matches against include and exclude patterns, {@code false} otherwise
   */
  private boolean matchAntPath(final String path, final Set<String> filters) {
    for (String filter : filters) {
      if (PathUtils.matchAntPath(path, filter)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if char sequence is empty(null or has no length).
   *
   * @param sequence sequence to check empty state
   * @return true if sequence is empty, false otherwise
   */
  private static boolean isEmpty(final CharSequence sequence) {
    return sequence == null || sequence.length() <= 0;
  }
}
