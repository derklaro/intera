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

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class DefaultIntera implements Intera {

  private final int maxCharsInRow;
  private final SubtractionValidator subtractionValidator;
  private final Map<Character, Integer> associations;

  protected DefaultIntera(int maxCharsInRow, SubtractionValidator subtractionValidator, Map<Character, Integer> associations) {
    this.maxCharsInRow = maxCharsInRow;
    this.subtractionValidator = subtractionValidator;
    this.associations = associations;
  }

  public static void main(String[] args) {
    DefaultIntera intera = new DefaultIntera(4, SubtractionValidator.defaults(), Utils.DEFAULT_ASSERTIONS);
    intera.write(30);
  }

  @Override
  public int parse(@NotNull String rome) throws InteraException {
    Utils.notNull(rome, "rome");
    final char[] chars = Utils.ensureValid(rome, this.maxCharsInRow);
    int result = 0;
    for (int i = 0; i < chars.length; i++) {
      final int association = this.associate(chars[i]);
      if (i < chars.length - 1) {
        final int nextAssociation = this.associate(chars[i + 1]);
        if (nextAssociation > association) {
          Utils.checkIllegalSubtraction(this.subtractionValidator, nextAssociation, association);
          result += nextAssociation - association;
          i++; // skip next
          continue;
        }
      }
      result += association;
    }
    return result;
  }

  @Override
  public @NotNull String write(int number) throws InteraException {
    final StringBuilder builder = new StringBuilder();
    final int highestPossible = this.getHighestAssociation();
    final Set<Map.Entry<Character, Integer>> entries = this.associations.entrySet();

    Map.Entry<String, Integer> lastFound = null;
    while (number > 0) {
      for (Map.Entry<Character, Integer> entry : entries) {
        if (lastFound == null || entry.getValue() > lastFound.getValue()) {
          if (number >= entry.getValue()) {
            lastFound = new AbstractMap.SimpleEntry<>(String.valueOf(entry.getKey()), entry.getValue());
          } else {
            Map.Entry<Character, Integer> entryValue = null;
            for (Map.Entry<Character, Integer> entry2 : entries) {
              if (entryValue == null) {
                entryValue = entry2;
              } else if (!this.subtractionValidator.isIllegalSubtraction(entry.getValue(), entry2.getValue())) {
                final int i = entry.getValue() - entry2.getValue();
                if (i > 0 && entry.getValue() - entryValue.getValue() < i) {
                  entryValue = entry2;
                }
              }
            }
            final int i = entryValue == null ? 0 : entry.getValue() - entryValue.getValue();
            if (i > 0 && number >= entry.getValue() - entryValue.getValue()) {
              lastFound = new AbstractMap.SimpleEntry<>(entryValue.getKey() + "" + entry.getKey(), entry.getValue() - entryValue.getValue());
            }
          }
        }
      }
      if (lastFound == null) {
        throw new InteraException("Unable find next node to travel over current number index " + number);
      }
      if (number < highestPossible && this.maxCharsInRow > 1 && builder.length() > 1) {
        int hits = 1;
        char c = builder.charAt(builder.length() - 1);
        while (builder.length() - hits >= 1 && hits <= this.maxCharsInRow) {
          final char before = builder.charAt(builder.length() - hits);
          if (before != c) {
            break;
          }
          c = before;
          hits++;
        }
        if (hits > this.maxCharsInRow + 1) {
          builder.delete(builder.length() - hits, builder.length());
          final int association = this.associate(c);
          final int targetNumber = association * hits;
          final Map.Entry<Character, Integer> next = this.upOne(association);

          for (Map.Entry<Character, Integer> entry : entries) {
            if (next.getValue() - entry.getValue() == targetNumber) {
              builder.append(entry.getKey()).append(next.getKey()).append(lastFound.getKey());
              number -= lastFound.getValue();
              lastFound = null;
              break;
            }
          }
          continue;
        }
      }
      builder.append(lastFound.getKey());
      number -= lastFound.getValue();
      lastFound = null;
    }

    return builder.toString();
  }

  private int associate(char c) throws InteraException {
    final Integer association = this.associations.get(c);
    if (association == null) {
      throw new InteraException("There is no known association for char " + c);
    }
    return association;
  }

  private Map.Entry<Character, Integer> upOne(int current) {
    Map.Entry<Character, Integer> last = null;
    for (Map.Entry<Character, Integer> entry : this.associations.entrySet()) {
      if (last == null || (entry.getValue() > current && last.getValue() > entry.getValue())) {
        last = entry;
      }
    }
    return last;
  }

  private int getHighestAssociation() {
    int result = 0;
    for (Integer value : this.associations.values()) {
      if (value > result) {
        result = value;
      }
    }
    return result;
  }
}
