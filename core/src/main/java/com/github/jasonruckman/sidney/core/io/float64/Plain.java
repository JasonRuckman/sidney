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

public class Plain {
  public static class PlainFloat64Decoder extends IndirectDecoder implements Float64Decoder {
    @Override
    public double nextDouble() {
      return input.readDouble();
    }

    @Override
    public double[] nextDoubles(int num) {
      return input.readDoubles(num);
    }
  }

  public static class PlainFloat64Encoder extends IndirectEncoder implements Float64Encoder {
    @Override
    public void writeDouble(double value) {
      output.writeDouble(value);
    }

    @Override
    public void writeDoubles(double[] values) {
      for (double value : values) {
        writeDouble(value);
      }
    }
  }
}