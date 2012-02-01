/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This interface represents utility methods for working with resource paths.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class PathUtils {
  public static final String PATH_SEPARATOR = "/";

  private static final Pattern ANT_TEXT_PATTERN = Pattern.compile("\\?|\\*");

  private static final String ANT_ANY_PATH_MARKER = "**";
  private static final String ANT_ANY_STRING_MARKER = "*";
  private static final String ANT_ANY_CHARACTER_MARKER = "?";

  /**
   * Tests whether a given path matches against ant-style pattern. The pattern may contain some special characters: <br/>
   * {@code '**'} means any path <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character.
   *
   * @param path    path which must be matched against the pattern, not {@code null}
   * @param pattern ant-style pattern to match against, not {@code null}
   * @return {@code true} if path matches against the pattern, {@code false} otherwise
   */
  public static boolean matchAntPath(final String path, final String pattern) {
    // When path starts with a /, pattern also has to start with a / and vice versa
    if (path.startsWith(PATH_SEPARATOR) != pattern.startsWith(PATH_SEPARATOR)) {
      return false;
    }

    final String[] patternDirs = pattern.split(PATH_SEPARATOR);
    final String[] pathDirs = path.split(PATH_SEPARATOR);

    int patternStart = 0;
    int patternEnd = patternDirs.length - 1;
    int pathStart = 0;
    int pathEnd = pathDirs.length - 1;

    // up to first '**'
    while (patternStart <= patternEnd && pathStart <= pathEnd) {
      if (patternDirs[patternStart].equals(ANT_ANY_PATH_MARKER)) {
        break;
      }
      if (!matchAntPattern(pathDirs[pathStart], patternDirs[patternStart])) {
        return false;
      }
      patternStart++;
      pathStart++;
    }
    if (pathStart > pathEnd) {
      // String is exhausted
      for (int i = patternStart; i <= patternEnd; i++) {
        if (!patternDirs[i].equals(ANT_ANY_PATH_MARKER)) {
          return false;
        }
      }
      return true;
    } else if (patternStart > patternEnd) {
      // String not exhausted, but pattern is. Failure.
      return false;
    }

    // up to last '**'
    while (patternStart <= patternEnd && pathStart <= pathEnd) {
      if (patternDirs[patternEnd].equals(ANT_ANY_PATH_MARKER)) {
        break;
      }
      if (!matchAntPattern(pathDirs[pathEnd], patternDirs[patternEnd])) {
        return false;
      }
      patternEnd--;
      pathEnd--;
    }
    if (pathStart > pathEnd) {
      // String is exhausted
      for (int i = patternStart; i <= patternEnd; i++) {
        if (!patternDirs[i].equals(ANT_ANY_PATH_MARKER)) {
          return false;
        }
      }
      return true;
    }

    while (patternStart != patternEnd && pathStart <= pathEnd) {
      int subPatternEnd = -1;
      for (int i = patternStart + 1; i <= patternEnd; i++) {
        if (patternDirs[i].equals(ANT_ANY_PATH_MARKER)) {
          subPatternEnd = i;
          break;
        }
      }
      if (subPatternEnd == patternStart + 1) {
        // '**/**' situation, so skip one
        patternStart++;
        continue;
      }
      // Find the pattern between padIdxStart & padIdxTmp in path between
      // pathStart & pathEnd
      final int patternLength = (subPatternEnd - patternStart - 1);
      final int pathLength = (pathEnd - pathStart + 1);
      int foundPosition = -1;

      strLoop:
      for (int i = 0; i <= pathLength - patternLength; i++) {
        for (int j = 0; j < patternLength; j++) {
          if (!matchAntPattern(pathDirs[pathStart + i + j], patternDirs[patternStart + j + 1])) {
            continue strLoop;
          }
        }

        foundPosition = pathStart + i;
        break;
      }
      if (foundPosition == -1) {
        return false;
      }

      patternStart = subPatternEnd;
      pathStart = foundPosition + patternLength;
    }

    for (int i = patternStart; i <= patternEnd; i++) {
      if (!patternDirs[i].equals(ANT_ANY_PATH_MARKER)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Tests whether a given string matches against a pattern. The pattern may contain two special characters: <br/>
   * {@code '*'} means zero or more characters <br/>
   * {@code '?'} means one and only one character
   *
   * @param text    string which must be matched against the pattern, not {@code null}
   * @param pattern pattern to match against, not {@code null}
   * @return {@code true} if the string matches against the pattern, {@code false} otherwise
   */
  public static boolean matchAntPattern(final String text, final String pattern) {
    final StringBuilder patternBuilder = new StringBuilder();
    final Matcher matcher = ANT_TEXT_PATTERN.matcher(pattern);
    int position = 0;
    while (matcher.find()) {
      patternBuilder.append(quote(pattern, position, matcher.start()));
      final String match = matcher.group();
      if (ANT_ANY_CHARACTER_MARKER.equals(match)) {
        patternBuilder.append('.');
      } else if (ANT_ANY_STRING_MARKER.equals(match)) {
        patternBuilder.append(".*");
      }
      position = matcher.end();
    }
    patternBuilder.append(quote(pattern, position, pattern.length()));
    return Pattern.compile(patternBuilder.toString()).matcher(text).matches();
  }

  /**
   * Quotes text in regexp pattern from specified start to end positions.
   *
   * @param text  text to be quoted
   * @param start start position
   * @param end   end position
   * @return regexp pattern with quoted text or empty string
   */
  private static String quote(final String text, final int start, final int end) {
    if (start >= end) {
      return "";
    }
    return Pattern.quote(text.substring(start, end));
  }
}
