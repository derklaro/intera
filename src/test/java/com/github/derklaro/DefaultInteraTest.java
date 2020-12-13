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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class DefaultInteraTest {

  @Test
  void testCorrectParse() {
    Assertions.assertEquals(3, Intera.defaults().parse("III"));
    Assertions.assertEquals(4, Intera.defaults().parse("IV"));
    Assertions.assertEquals(2824, Intera.defaults().parse("MMDCCCXXIV"));
    Assertions.assertEquals(1426, Intera.defaults().parse("MCDXXVI"));
    Assertions.assertEquals(2475, Intera.defaults().parse("MMCDLXXV"));
    Assertions.assertEquals(2024, Intera.defaults().parse("MMXXIV"));
    Assertions.assertEquals(1780, Intera.defaults().parse("MDCCLXXX"));
  }

  @Test
  void testInvalidParse() {
    // Illegal subtraction
    Assertions.assertThrows(InteraException.class, () -> Intera.defaults().parse("IC"));
    // Too many numbers of type 'I' (max 3)
    Assertions.assertThrows(InteraException.class, () -> Intera.defaults().parse("IIII"));
    // Unknown number char
    Assertions.assertThrows(InteraException.class, () -> Intera.defaults().parse("VQII"));
  }

  @Test
  void testCorrectSerialize() {
    Assertions.assertEquals("III", Intera.defaults().write(3));
    Assertions.assertEquals("IV", Intera.defaults().write(4));
    Assertions.assertEquals("MMDCCCXXIV", Intera.defaults().write(2824));
    Assertions.assertEquals("MCDXXVI", Intera.defaults().write(1426));
    Assertions.assertEquals("MMCDLXXV", Intera.defaults().write(2475));
    Assertions.assertEquals("MMXXIV", Intera.defaults().write(2024));
    Assertions.assertEquals("MDCCLXXX", Intera.defaults().write(1780));
  }
}
