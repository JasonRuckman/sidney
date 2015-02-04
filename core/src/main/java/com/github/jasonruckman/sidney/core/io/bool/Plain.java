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
package com.github.jasonruckman.sidney.core.io.bool;

import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

public class Plain {
  public static class PlainBoolDecoder extends BaseDecoder implements BoolDecoder {
    @Override
    public boolean nextBool() {
      return readBooleanFromBuffer();
    }

    @Override
    public boolean[] nextBools(int num) {
      boolean[] booleans = new boolean[num];
      for (int i = 0; i < num; i++) {
        booleans[i] = readBooleanFromBuffer();
      }
      return booleans;
    }
  }

  public static class PlainBoolEncoder extends BaseEncoder implements BoolEncoder {
    @Override
    public void writeBool(boolean value) {
      writeBooleanToBuffer(value);
    }

    @Override
    public void writeBools(boolean[] values) {
      for (boolean value : values) {
        writeBool(value);
      }
    }
  }
}
