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
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class RLE {
  private static final Encoding VALUE_ENCODING = Encoding.PLAIN;
  private static final Encoding RUN_SIZE_ENCODING = Encoding.PLAIN;

  public static class RLEInt64Decoder implements Int64Decoder {
    private final Int64Decoder valueDecoder = VALUE_ENCODING.newInt64Decoder();
    private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();
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
      currentRun = valueDecoder.nextLong();
      runSize = runSizeDecoder.nextInt();
    }

    @Override
    public void initialize(Input input) {
      runSize = 0;
      currentRun = 0;
      valueDecoder.initialize(input);
      runSizeDecoder.initialize(input);
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class RLEInt64Encoder implements Int64Encoder {
    private Int64Encoder valueEncoder = VALUE_ENCODING.newInt64Encoder();
    private Int32Encoder runSizeEncoder = RUN_SIZE_ENCODING.newInt32Encoder();

    private long currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeLong(long value, Output output) {
      if (isNewRun) {
        currentRun = value;
        isNewRun = false;
      } else if (currentRun != value) {
        flush(output);
        currentRun = value;
      }
      ++runSize;
    }

    @Override
    public void writeLongs(long[] longs, Output output) {
      for (long v : longs) {
        writeLong(v, output);
      }
    }

    @Override
    public void reset() {
      valueEncoder.reset();
      runSizeEncoder.reset();
      isNewRun = true;
      currentRun = 0;
      runSize = 0;
    }

    @Override
    public void flush(Output output) {
      flushRun(output);
    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }

    private void flushRun(Output output) {
      valueEncoder.writeLong(currentRun, output);
      runSizeEncoder.writeInt(runSize, output);

      currentRun = 0;
      runSize = 0;
    }
  }
}
