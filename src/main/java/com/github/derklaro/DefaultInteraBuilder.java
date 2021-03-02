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
import java.util.concurrent.ConcurrentHashMap;

/**
 * An default implementation of {@link Intera.Builder}. Use {@link Intera#builder()} to
 * create an instance of this class.
 *
 * @author Pasqual Koschmieder
 * @since 1.0.0
 */
final class DefaultInteraBuilder implements Intera.Builder {
  /**
   * The rome to arabic number mappings to use.
   */
  private final Map<Character, Integer> associations = new ConcurrentHashMap<>();
  /**
   * The maximum rome numbers allowed in a row.
   */
  private int maxCharsInRow = 3;
  /**
   * The subtraction validator to use.
   */
  private SubtractionValidator subtractionValidator = SubtractionValidator.defaults();

  /**
   * {@inheritDoc}
   */
  @Override
  public Intera.@NotNull Builder defaultAssociations() {
    this.associations.clear();
    this.associations.putAll(Utils.DEFAULT_ASSERTIONS);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Intera.@NotNull Builder registerAssociation(char romeNumberChar, int arabicValue) {
    this.associations.put(romeNumberChar, arabicValue);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Intera.@NotNull Builder registerAssociations(@NotNull Map<Character, Integer> romeToArabicNumbers) {
    Utils.notNull(romeToArabicNumbers, "romeToArabicNumbers");
    this.associations.putAll(romeToArabicNumbers);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Intera.@NotNull Builder subtractionValidator(@NotNull SubtractionValidator validator) {
    Utils.notNull(validator, "validator");
    this.subtractionValidator = validator;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Intera.@NotNull Builder maxSameCharsInRow(int maxChars) {
    this.maxCharsInRow = maxChars;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NotNull Intera build() {
    if (this.associations.isEmpty()) {
      throw new InteraException("At least one association is required");
    }
    return new DefaultIntera(this.maxCharsInRow, this.subtractionValidator, this.associations);
  }
}
