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

public class CustomInteraTest {

  @Test
  void testNoAssociations() {
    Assertions.assertThrows(InteraException.class, () -> Intera.builder().build());
  }

  @Test
  void testNoSubtractionLimits() {
    Assertions.assertEquals(95, Intera.builder()
      .defaultAssociations()
      .subtractionValidator(SubtractionValidator.disabled())
      .build()
      .parse("VC")
    );
    Assertions.assertEquals("MDCIC", Intera.builder()
      .defaultAssociations()
      .subtractionValidator(SubtractionValidator.disabled())
      .build()
      .write(1699)
    );
  }

  @Test
  void testMaxCharsInRow() {
    Assertions.assertEquals(4, Intera.builder()
      .defaultAssociations()
      .maxSameCharsInRow(4)
      .build()
      .parse("IIII")
    );
    Assertions.assertEquals("MMMM", Intera.builder()
      .defaultAssociations()
      .maxSameCharsInRow(4)
      .build()
      .write(4000)
    );
  }
}
