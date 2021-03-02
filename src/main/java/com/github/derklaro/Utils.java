/*
 * This file is part of intera, licensed under the MIT License (MIT).
 *
 * Copyright (c) Pasqual Koschmieder <https://github.com/derklaro>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.derklaro;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An internal class holding some util method {@literal &} jvm static fields.
 *
 * @author Pasqual Koschmieder
 * @since 1.0.0
 */
final class Utils {
  /**
   * The default assertions between rome and arabic numbers, before Map.of() was invented.
   */
  public static final Map<Character, Integer> DEFAULT_ASSERTIONS = createDefaultAssertions();
  /**
   * A jvm static subtraction validator which allows all subtractions.
   */
  public static final SubtractionValidator DISABLED = (i1, i2) -> Boolean.FALSE;
  /**
   * The jvm static default instance of intera, using the default mappings.
   */
  public static final Intera DEFAULT_IMPL = Intera.builder().defaultAssociations().build();

  private Utils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Checks if a subtraction is illegally made according to the rules of the given {@code validator}.
   *
   * @param validator The validator which checks the subtraction.
   * @param current   The number from which the subtraction will be made.
   * @param before    The number which gets subtracted.
   * @throws InteraException If the subtraction is illegally made.
   */
  public static void checkIllegalSubtraction(@NotNull SubtractionValidator validator, int current, int before) {
    if (validator.isIllegalSubtraction(current, before)) {
      throw new InteraException("Cannot subtract " + before + " from " + current);
    }
  }

  /**
   * Throws an exception if the supplied {@code object} is {@code null}.
   *
   * @param object  The object to check.
   * @param message The detail message of the exception.
   * @throws InteraException If the supplied object is null.
   */
  public static void notNull(@Nullable Object object, @NotNull String message) {
    if (object == null) {
      throw new InteraException(message);
    }
  }

  /**
   * Ensures that the provided rome number count in a row is used.
   *
   * @param rome     The rome text string.
   * @param maxInRow The max rome numbers allowed in a row. Less than {@code 1} means disabled.
   * @return The chars of the given {@code rome} input.
   * @throws InteraException If there are more chars in a row than the maximum allowed.
   */
  public static char[] ensureValid(@NotNull String rome, int maxInRow) {
    final char[] chars = rome.toCharArray();
    if (maxInRow <= 1) {
      return chars;
    }

    for (int i = 0; i < chars.length; i++) {
      if (i >= maxInRow) {
        ensureValid(chars, i, maxInRow - 1);
      }
    }
    return chars;
  }

  /**
   * Ensures that the provided rome number count in a row is used.
   *
   * @param chars    The chars of the rome text string.
   * @param index    The index to start the check at.
   * @param maxInRow The max rome numbers allowed in a row.
   * @throws InteraException If there are more chars in a row than the maximum allowed.
   */
  private static void ensureValid(char[] chars, int index, int maxInRow) {
    final char current = chars[index];
    for (int i = 0; i < maxInRow; i++) {
      if (chars[i] != current) {
        return;
      }
    }
    throw new InteraException("More than " + maxInRow + " chars of " + current + " in a row");
  }

  /**
   * Provides the default mappings for {@link Utils#DEFAULT_ASSERTIONS} before Map.of() was invented.
   *
   * @return the default mappings for {@link Utils#DEFAULT_ASSERTIONS}.
   */
  private static @NotNull Map<Character, Integer> createDefaultAssertions() {
    final Map<Character, Integer> assertions = new ConcurrentHashMap<>();
    assertions.put('I', 1);
    assertions.put('V', 5);
    assertions.put('X', 10);
    assertions.put('L', 50);
    assertions.put('C', 100);
    assertions.put('D', 500);
    assertions.put('M', 1000);
    return Collections.unmodifiableMap(assertions);
  }
}
