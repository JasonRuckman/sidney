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

import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;

public class RLE {
  public static class RLEInt64Decoder extends IndirectDecoder implements Int64Decoder {
    private int runSize = 0;
    private long currentRun = 0;

    public long nextLong() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }

      return currentRun;
    }

    @Override
    public long[] nextLongs(int num) {
      long[] longs = new long[num];
      for (int i = 0; i < num; i++) {
        longs[i] = nextLong();
      }
      return longs;
    }

    private void loadNextRun() {
      currentRun = input.readLong();
      runSize = input.readInt();
    }

    @Override
    public void load() {
      runSize = 0;
      currentRun = 0;

      loadNextRun();
    }
  }

  public static class RLEInt64Encoder extends IndirectEncoder implements Int64Encoder {
    private long currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeLong(long value) {
      if (isNewRun) {
        currentRun = value;
        isNewRun = false;
      } else if (currentRun != value) {
        flush();
        currentRun = value;
      }
      ++runSize;
    }

    @Override
    public void writeLongs(long[] longs) {
      for (long v : longs) {
        writeLong(v);
      }
    }

    @Override
    public void reset() {
      isNewRun = true;
      currentRun = 0;
      runSize = 0;
    }

    @Override
    public void flush() {
      flushRun();
    }

    private void flushRun() {
      output.writeLong(currentRun);
      output.writeInt(runSize);

      currentRun = 0;
      runSize = 0;
    }
  }
}
