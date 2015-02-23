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

import com.github.jasonruckman.sidney.core.io.input.Input;
import com.github.jasonruckman.sidney.core.io.output.Output;
import com.github.jasonruckman.sidney.core.io.strategies.*;

public class Plain {
  public static class PlainStringDecoder implements StringDecoder {
    private Input input;

    public String readString() {
      return input.readUtf8();
    }

    @Override
    public String[] readStrings(int num) {
      String[] strings = new String[num];
      for (int i = 0; i < num; i++) {
        strings[i] = readString();
      }
      return strings;
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

  public static class PlainStringEncoder implements StringEncoder {
    public void writeString(String s, Output output) {
      output.writeUtf8(s);
    }

    @Override
    public void writeStrings(String[] strings, Output output) {
      for (String s : strings) {
        writeString(s, output);
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