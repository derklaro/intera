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

final class Utils {

  public static final Map<Character, Integer> DEFAULT_ASSERTIONS = createDefaultAssertions();
  public static final SubtractionValidator DEFAULT_VALIDATOR = Utils::isIllegalSubtraction;
  public static final SubtractionValidator DISABLED = (i1, i2) -> Boolean.FALSE;
  public static final Intera DEFAULT_IMPL = Intera.builder().defaultAssociations().build();

  private Utils() {
    throw new UnsupportedOperationException();
  }

  public static void checkIllegalSubtraction(@NotNull SubtractionValidator validator, int current, int before) {
    if (validator.isIllegalSubtraction(current, before)) {
      throw new InteraException("Cannot subtract " + before + " from " + current);
    }
  }

  public static void notNull(@Nullable Object object, @NotNull String message) {
    if (object == null) {
      throw new InteraException(message);
    }
  }

  private static boolean isIllegalSubtraction(int current, int before) {
    switch (before) {
      case 1:
        return current != 5 && current != 10;
      case 10:
        return current != 50 && current != 100;
      case 100:
        return current != 500 && current != 1000;
      default:
        return true;
    }
  }

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

  private static void ensureValid(char[] chars, int index, int maxInRow) {
    final char current = chars[index];
    for (int i = 0; i < maxInRow; i++) {
      if (chars[i] != current) {
        return;
      }
    }
    throw new InteraException("More than " + maxInRow + " chars of " + current + " in a row");
  }

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
