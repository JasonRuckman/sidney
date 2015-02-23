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
package com.github.jasonruckman.sidney.core.io.int32;

import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class RLE {
  private static final Encoding VALUE_ENCODING = Encoding.PLAIN;
  private static final Encoding RUN_SIZE_ENCODING = Encoding.PLAIN;

  public static class RLEInt32Decoder implements Int32Decoder {
    private final Int32Decoder valueDecoder = VALUE_ENCODING.newInt32Decoder();
    private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();
    private int runSize = 0;
    private int currentRun = 0;

    public int nextInt() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }

      return currentRun;
    }

    @Override
    public int[] nextInts(int num) {
      int[] ints = new int[num];
      for (int i = 0; i < num; i++) {
        ints[i] = nextInt();
      }
      return ints;
    }

    private void loadNextRun() {
      currentRun = valueDecoder.nextInt();
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

  public static class RLEInt32Encoder implements Int32Encoder {
    private final Int32Encoder valueEncoder = VALUE_ENCODING.newInt32Encoder();
    private final Int32Encoder runSizeEncoder = VALUE_ENCODING.newInt32Encoder();
    private int currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeInt(int value, Output output) {
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
    public void writeInts(int[] ints, Output output) {
      for (int v : ints) {
        writeInt(v, output);
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
      flushWord(output);
    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }

    private void flushWord(Output output) {
      valueEncoder.writeInt(currentRun, output);
      runSizeEncoder.writeInt(runSize, output);

      currentRun = 0;
      runSize = 0;
    }
  }
}