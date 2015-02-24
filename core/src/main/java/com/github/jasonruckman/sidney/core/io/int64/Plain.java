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
package com.github.jasonruckman.sidney.core.io.int64;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.input.Input;

public class Plain {
  public static class PlainInt64Decoder extends IndirectDecoder implements Int64Decoder {
    @Override
    public long nextLong() {
      return input.readLong();
    }

    @Override
    public long[] nextLongs(int num) {
      return input.readLongs(num);
    }
  }

  public static class PlainInt64Encoder extends IndirectEncoder implements Int64Encoder {
    @Override
    public void writeLong(long value) {
      output.writeLong(value);
    }

    @Override
    public void writeLongs(long[] values) {
      for (long l : values) {
        writeLong(l);
      }
    }
  }
}