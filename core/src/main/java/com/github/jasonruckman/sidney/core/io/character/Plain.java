/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jasonruckman.sidney.core.io.character;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;

public class Plain {
  public static class PlainCharDecoder extends IndirectDecoder implements CharDecoder {
    @Override
    public char nextChar() {
      return input.readChar();
    }

    @Override
    public char[] nextChars(int num) {
      return input.readChars(num);
    }
  }

  public static class PlainCharEncoder extends IndirectEncoder implements CharEncoder {
    @Override
    public void writeChar(char value) {
      output.writeChar(value);
    }

    @Override
    public void writeChars(char[] values) {
      output.writeChars(values);
    }
  }
}