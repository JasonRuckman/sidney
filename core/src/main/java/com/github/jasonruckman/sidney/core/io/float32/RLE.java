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
package com.github.jasonruckman.sidney.core.io.float32;

import com.github.jasonruckman.sidney.core.io.IndirectDecoder;
import com.github.jasonruckman.sidney.core.io.IndirectEncoder;
import com.github.jasonruckman.sidney.core.io.output.Output;

public class RLE {
  public static class RLEFloat32Decoder extends IndirectDecoder implements Float32Decoder {
    private int runSize = 0;
    private float currentRun = 0;

    public float nextFloat() {
      if (runSize-- == 0) {
        loadNextRun();
        runSize--;
      }

      return currentRun;
    }

    @Override
    public float[] nextFloats(int num) {
      float[] floats = new float[num];
      for (int i = 0; i < num; i++) {
        floats[i] = nextFloat();
      }
      return floats;
    }

    private void loadNextRun() {
      currentRun = input.readFloat();
      runSize = input.readInt();
    }

    @Override
    public void load() {
      runSize = 0;
      currentRun = 0;

      loadNextRun();
    }
  }

  public static class RLEFloat32Encoder extends IndirectEncoder implements Float32Encoder {
    private float currentRun = 0;
    private int runSize = 0;
    private boolean isNewRun = true;

    @Override
    public void writeFloat(float value) {
      if (isNewRun) {
        currentRun = value;
        isNewRun = false;
      } else if (Float.compare(currentRun, value) != 0) {
        flush();
        currentRun = value;
      }
      ++runSize;
    }

    @Override
    public void writeFloats(float[] floats) {
      for (float v : floats) {
        writeFloat(v);
      }
    }

    private void flushRun(Output output) {
      output.writeFloat(currentRun);
      output.writeInt(runSize);

      currentRun = 0;
      runSize = 0;
    }

    @Override
    public void reset() {
      isNewRun = true;
      currentRun = 0;
      runSize = 0;
    }

    @Override
    public void flush() {
      flushRun(output);
    }
  }
}