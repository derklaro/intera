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

import java.util.Map;

/**
 * The top most api class of the library, open for further implementations. By default
 * this class is implemented by {@link DefaultIntera} and an instance of the default
 * implementation (with the default mappings) is available using {@link Intera#defaults()}.
 * This instance is jvm static, meaning it will not create a new instance every time you
 * invoke that method. If you need to change any setting, use {@link Intera#builder()} and
 * use the provided methods to customize the intera parsing behaviour.
 *
 * @author Pasqual Koschmieder
 * @since 1.0.0
 */
public interface Intera {
  /**
   * Obtain the default implementation of the intera instance with the default mappings
   * applied to it.
   *
   * @return the jvm static default instance of the intera parser.
   */
  static @NotNull Intera defaults() {
    return Utils.DEFAULT_IMPL;
  }

  /**
   * Creates a new builder instance for intera.
   *
   * @return a new builder instance for intera.
   */
  static @NotNull Builder builder() {
    return new DefaultInteraBuilder();
  }

  /**
   * Parses the given {@code romeNumberText} to an integer using the provided
   * mappings in the intera builder class.
   *
   * @param romeNumberText The rome number text to parse.
   * @return The arabic value of the rome number.
   * @throws InteraException If any exception occurs during the parse process.
   */
  int parse(@NotNull String romeNumberText) throws InteraException;

  /**
   * Write the given {@code number} to an rome number string.
   *
   * @param number The number to write.
   * @return The rome association with the given number.
   * @throws InteraException If any exception occurs during the write.
   */
  @NotNull String write(int number) throws InteraException;

  /**
   * A builder for an intera instance.
   */
  interface Builder {
    /**
     * Applies the default arabic {@literal <}-{@literal >} rome mappings to the builder instance.
     *
     * @return The same instance of the class, for chaining.
     */
    @NotNull Builder defaultAssociations();

    /**
     * Registers an association between the rome char and the provided
     * arabic number.
     *
     * @param romeNumberChar The char to associate with the arabic number.
     * @param arabicValue    The arabic number to associate with the rome number char.
     * @return The same instance of the class, for chaining.
     */
    @NotNull Builder registerAssociation(char romeNumberChar, int arabicValue);

    /**
     * Registers all associations in the map.
     *
     * @param romeToArabicNumbers The arabic {@literal <}-{@literal >} rome mappings
     *                            to register.
     * @return The same instance of this class, for chaining.
     * @see #registerAssociation(char, int)
     */
    @NotNull Builder registerAssociations(@NotNull Map<Character, Integer> romeToArabicNumbers);

    /**
     * Sets the subtraction validator.
     *
     * @param validator The validator to use.
     * @return The same instance of this class, for chaining.
     */
    @NotNull Builder subtractionValidator(@NotNull SubtractionValidator validator);

    /**
     * Sets the maximum amount of same rome number chars in a row. Defaults to {@code 3}.
     *
     * @param maxChars The max chars which are allowed in a row.
     * @return The same instance of this class, for chaining.
     */
    @NotNull Builder maxSameCharsInRow(int maxChars);

    /**
     * Builds an intera instance with the provided options.
     *
     * @return The created intera instance from this builder.
     */
    @NotNull Intera build();
  }
}
