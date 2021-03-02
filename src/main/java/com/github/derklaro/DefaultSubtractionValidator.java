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

/**
 * A subtraction validator which checks if the default subtraction rules as listed
 * <a href="https://simple.wikipedia.org/wiki/Roman_numerals#Subtraction_rule">here</a> are used.
 *
 * @author Pasqual Koschmieder
 * @since 1.0.1
 */
final class DefaultSubtractionValidator implements SubtractionValidator {
  /**
   * The jvm static instance of the default subtraction validator.
   */
  public static final DefaultSubtractionValidator INSTANCE = new DefaultSubtractionValidator();

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isIllegalSubtraction(int number, int numberToSubtract) {
    switch (numberToSubtract) {
      case 1:
        return number != 5 && number != 10;
      case 10:
        return number != 50 && number != 100;
      case 100:
        return number != 500 && number != 1000;
      default:
        return true;
    }
  }
}
