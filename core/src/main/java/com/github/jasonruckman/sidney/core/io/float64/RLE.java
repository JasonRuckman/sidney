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
package com.github.jasonruckman.sidney.core.io.float64;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;

public class RLE {
  public static class RLEFloat64Decoder extends IndirectDecoder implements Float64Decoder {
    private int runSize = 0;
    private double currentRun = 0;

    @Override
    public double nextDouble() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }

      return currentRun;
    }

    @Override
    public double[] nextDoubles(int num) {
      double[] floats = new double[num];
      for (int i = 0; i < num; i++) {
        floats[i] = nextDouble();
      }
      return floats;
    }

    private void loadNextRun() {
      currentRun = input.readDouble();
      runSize = input.readInt();
    }

    @Override
    public void load() {
      runSize = 0;
      currentRun = 0;

      loadNextRun();
    }
  }

  public static class RLEFloat64Encoder extends IndirectEncoder implements Float64Encoder {
    private double currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeDouble(double value) {
      if (isNewRun) {
        currentRun = value;
        isNewRun = false;
      } else if (Double.compare(currentRun, value) != 0) {
        flushWord();
        currentRun = value;
      }
      ++runSize;
    }

    @Override
    public void writeDoubles(double[] floats) {
      for (double v : floats) {
        writeDouble(v);
      }
    }

    private void flushWord() {
      output.writeDouble(currentRun);
      output.writeInt(runSize);

      currentRun = 0;
      runSize = 0;
    }

    @Override
    public void flush() {
      flushWord();
    }

    @Override
    public void reset() {
      isNewRun = true;
      currentRun = 0;
      runSize = 0;
    }
  }
}