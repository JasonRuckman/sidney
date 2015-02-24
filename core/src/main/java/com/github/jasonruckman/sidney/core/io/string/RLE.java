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

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;

public class RLE {
  public static class RLEStringDecoder extends IndirectDecoder implements StringDecoder {
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
      currentRun = input.readUtf8();
      runSize = input.readInt();
    }

    @Override
    public void load() {
      runSize = 0;
      currentRun = null;
    }
  }

  public static class RLEStringEncoder extends IndirectEncoder implements StringEncoder {
    private String currentRun = "";
    private int runSize;
    private boolean isNewRun = true;

    @Override
    public void writeString(String s) {
      if (isNewRun) {
        currentRun = s;
        isNewRun = false;
      } else if (!s.equals(currentRun)) {
        flush();
        currentRun = s;
      }
      ++runSize;
    }

    @Override
    public void writeStrings(String[] s) {
      for (String str : s) {
        writeString(str);
      }
    }

    @Override
    public void reset() {
      currentRun = "";
      runSize = 0;
      isNewRun = true;
    }

    @Override
    public void flush() {
      flushRun();
    }

    private void flushRun() {
      output.writeUtf8(currentRun);
      output.writeInt(runSize);
      currentRun = "";
      runSize = 0;
    }
  }
}