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

/**
 * An interface called by an {@link Intera} instance to check if an subtraction is illegal or not.
 * See <a href="https://simple.wikipedia.org/wiki/Roman_numerals#Subtraction_rule">here</a> which
 * subtractions are allowed by default. A default checker is submitted by {@link SubtractionValidator#defaults()}.
 *
 * @author Pasqual Koschmieder
 * @since 1.0.0
 */
@FunctionalInterface
public interface SubtractionValidator {
  /**
   * Get a jvm static subtraction validator which does not check anything.
   *
   * @return a jvm static subtraction validator which does not check anything.
   */
  static @NotNull SubtractionValidator disabled() {
    return Utils.DISABLED;
  }

  /**
   * Get a jvm static subtraction validator which checks if the default subtraction rules
   * as listed <a href="https://simple.wikipedia.org/wiki/Roman_numerals#Subtraction_rule">here</a>
   * are used.
   *
   * @return a jvm static subtraction validator which checks for the default subtraction rules.
   */
  static @NotNull SubtractionValidator defaults() {
    return DefaultSubtractionValidator.INSTANCE;
  }

  /**
   * Checks if a subtraction is invalid.
   *
   * @param number           The number from which the subtraction will be made.
   * @param numberToSubtract The number which gets subtracted.
   * @return If the subtraction is against the rules of the validator or not.
   */
  boolean isIllegalSubtraction(int number, int numberToSubtract);
}
