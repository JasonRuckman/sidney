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
package com.github.jasonruckman.sidney.core.io.int16;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.output.Output;

public class RLE {
  public static class RLEShortDecoder extends IndirectDecoder implements ShortDecoder {
    private int runSize = 0;
    private short currentRun = 0;

    @Override
    public short nextShort() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }

      return currentRun;
    }

    @Override
    public short[] nextShorts(int num) {
      short[] ints = new short[num];
      for (int i = 0; i < num; i++) {
        ints[i] = nextShort();
      }
      return ints;
    }

    private void loadNextRun() {
      currentRun = input.readShort();
      runSize = input.readInt();
    }

    @Override
    public void load() {
      runSize = 0;
      currentRun = 0;

      loadNextRun();
    }
  }

  public static class RLEShortEncoder extends IndirectEncoder implements ShortEncoder {
    private short currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeShort(short value) {
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
    public void writeShorts(short[] shorts) {
      for (short v : shorts) {
        writeShort(v);
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
      flushWord(output);
    }

    private void flushWord(Output output) {
      output.writeShort(currentRun);
      output.writeInt(runSize);

      currentRun = 0;
      runSize = 0;
    }
  }
}