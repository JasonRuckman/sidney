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
package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.IntFunction;
import com.github.jasonruckman.sidney.core.io.float64.Float64Decoder;
import com.github.jasonruckman.sidney.core.io.float64.Float64Encoder;
import com.github.jasonruckman.sidney.core.io.float64.Plain;
import com.github.jasonruckman.sidney.core.io.float64.RLE;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Float64Test extends AbstractEncoderTests<Float64Encoder, Float64Decoder, double[]> {
  private final List<EncoderDecoderPair<Float64Encoder, Float64Decoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<Float64Encoder, Float64Decoder>(new Plain.PlainFloat64Encoder(), new Plain.PlainFloat64Decoder()),
      new EncoderDecoderPair<Float64Encoder, Float64Decoder>(new RLE.RLEFloat64Encoder(), new RLE.RLEFloat64Decoder())
  );

  @Override
  protected BiConsumer<Float64Encoder, double[]> encodingFunction() {
    return new BiConsumer<Float64Encoder, double[]>() {
      @Override
      public void accept(Float64Encoder encoder, double[] nums) {
        for (double num : nums) {
          encoder.writeDouble(num);
        }
      }
    };
  }

  @Override
  protected IntFunction<double[]> dataSupplier() {
    return new IntFunction<double[]>() {
      @Override
      public double[] apply(int size) {
        double[] doubles = new double[size];
        Random random = new Random(11L);
        for (int i = 0; i < size; i++) {
          doubles[i] = random.nextGaussian();
        }
        return doubles;
      }
    };
  }

  @Override
  protected BiConsumer<Float64Decoder, double[]> consumeAndAssert() {
    return new BiConsumer<Float64Decoder, double[]>() {
      @Override
      public void accept(Float64Decoder decoder, double[] doubles) {
        double[] ints = decoder.nextDoubles(doubles.length);
        Assert.assertArrayEquals(doubles, ints, 0);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<Float64Encoder, Float64Decoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return this.getClass();
  }
}