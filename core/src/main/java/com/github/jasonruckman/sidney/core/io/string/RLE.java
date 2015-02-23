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
package com.github.jasonruckman.sidney.core.io.string;

import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class RLE {
  private static final Encoding RUN_SIZE_ENCODING = Encoding.PLAIN;

  public static class RLEStringDecoder implements StringDecoder {
    private final StringDecoder valueDecoder = Encoding.PLAIN.newStringDecoder();
    private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();

    private int runSize = 0;
    private String currentRun = null;

    @Override
    public String readString() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }
      return currentRun;
    }

    @Override
    public String[] readStrings(int num) {
      String[] strings = new String[num];
      for (int i = 0; i < num; i++) {
        strings[i] = readString();
      }
      return strings;
    }

    private void loadNextRun() {
      currentRun = valueDecoder.readString();
      runSize = runSizeDecoder.nextInt();
    }

    @Override
    public void initialize(Input input) {
      runSize = 0;
      currentRun = null;

      valueDecoder.initialize(input);
      runSizeDecoder.initialize(input);
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class RLEStringEncoder implements StringEncoder {
    private final Int32Encoder runSizeEncoder = RUN_SIZE_ENCODING.newInt32Encoder();
    private final StringEncoder valueEncoder = new Plain.PlainStringEncoder();
    private String currentRun = "";
    private int runSize;
    private boolean isNewRun = true;

    @Override
    public void writeString(String s, Output output) {
      if (isNewRun) {
        currentRun = s;
        isNewRun = false;
      } else if (!s.equals(currentRun)) {
        flush(output);
        currentRun = s;
      }
      ++runSize;
    }

    @Override
    public void writeStrings(String[] s, Output output) {
      for (String str : s) {
        writeString(str, output);
      }
    }

    @Override
    public void reset() {
      valueEncoder.reset();
      runSizeEncoder.reset();
      currentRun = "";
      runSize = 0;
      isNewRun = true;
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
      valueEncoder.writeString(currentRun, output);
      runSizeEncoder.writeInt(runSize, output);
      currentRun = "";
      runSize = 0;
    }
  }
}