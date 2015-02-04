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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
      runSize = 0;
      currentRun = 0;

      valueDecoder.readFromStream(inputStream);
      runSizeDecoder.readFromStream(inputStream);
    }

    private void loadNextRun() {
      currentRun = valueDecoder.nextInt();
      runSize = runSizeDecoder.nextInt();
    }
  }

  public static class RLEInt32Encoder implements Int32Encoder {
    private final Int32Encoder valueEncoder = VALUE_ENCODING.newInt32Encoder();
    private final Int32Encoder runSizeEncoder = VALUE_ENCODING.newInt32Encoder();
    private int currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeInt(int value) {
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
    public void writeInts(int[] ints) {
      for (int v : ints) {
        writeInt(v);
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
    public void writeToStream(OutputStream outputStream) throws IOException {
      flush();

      valueEncoder.writeToStream(outputStream);
      runSizeEncoder.writeToStream(outputStream);
    }

    private void flush() {
      valueEncoder.writeInt(currentRun);
      runSizeEncoder.writeInt(runSize);

      currentRun = 0;
      runSize = 0;
    }
  }
}
