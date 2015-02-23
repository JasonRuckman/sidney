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
package com.github.jasonruckman.sidney.core.io.bool;

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class Plain {
  public static class PlainBoolDecoder implements BoolDecoder {
    private Input input;

    @Override
    public boolean nextBool() {
      return input.readBoolean();
    }

    @Override
    public boolean[] nextBools(int num) {
      boolean[] booleans = new boolean[num];
      for (int i = 0; i < num; i++) {
        booleans[i] = nextBool();
      }
      return booleans;
    }

    @Override
    public void initialize(Input input) {
      this.input = input;
    }

    @Override
    public ColumnLoadStrategy strategy() {
      return new Default.DefaultColumnLoadStrategy();
    }
  }

  public static class PlainBoolEncoder implements BoolEncoder {
    @Override
    public void writeBool(boolean value, Output output) {
      output.writeBoolean(value);
    }

    @Override
    public void writeBools(boolean[] values, Output output) {
      for (boolean value : values) {
        writeBool(value, output);
      }
    }

    @Override
    public void reset() {

    }

    @Override
    public void flush(Output output) {

    }

    @Override
    public ColumnWriteStrategy strategy() {
      return new Default.DefaultColumnWriteStrategy();
    }
  }
}