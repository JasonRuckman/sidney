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

import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;

public class Plain {
  public static class PlainInt64Decoder extends BaseDecoder implements Int64Decoder {
    @Override
    public long nextLong() {
      return readLongFromBuffer();
    }

    @Override
    public long[] nextLongs(int num) {
      long[] longs = new long[num];
      for (int i = 0; i < num; i++) {
        longs[i] = readLongFromBuffer();
      }
      return longs;
    }
  }

  public static class PlainInt64Encoder extends BaseEncoder implements Int64Encoder {
    @Override
    public void writeLong(long value) {
      writeLongToBuffer(value);
    }

    @Override
    public void writeLongs(long[] values) {
      for (long l : values) {
        writeLong(l);
      }
    }
  }
}
