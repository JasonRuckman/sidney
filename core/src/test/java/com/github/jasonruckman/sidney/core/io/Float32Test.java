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
import com.github.jasonruckman.sidney.core.io.float32.Float32Decoder;
import com.github.jasonruckman.sidney.core.io.float32.Float32Encoder;
import com.github.jasonruckman.sidney.core.io.float32.Plain;
import com.github.jasonruckman.sidney.core.io.float32.RLE;
import com.github.jasonruckman.sidney.core.io.output.Output;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Float32Test extends AbstractEncoderTests<Float32Encoder, Float32Decoder, float[]> {
  private final List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<Float32Encoder, Float32Decoder>(new Plain.PlainFloat32Encoder(), new Plain.PlainFloat32Decoder()),
      new EncoderDecoderPair<Float32Encoder, Float32Decoder>(new RLE.RLEFloat32Encoder(), new RLE.RLEFloat32Decoder())
  );

  protected TriConsumer<Output, Float32Encoder, float[]> encodingFunction() {
    return new TriConsumer<Output, Float32Encoder, float[]>() {
      @Override
      public void accept(Output output, Float32Encoder encoder, float[] floats) {
        if(!encoder.isDirect()) encoder.asIndirect().setOutput(output);
        encoder.writeFloats(floats);
      }
    };
  }

  @Override
  protected IntFunction<float[]> dataSupplier() {
    return new IntFunction<float[]>() {
      @Override
      public float[] apply(int size) {
        float[] floats = new float[size];
        Random random = new Random(11L);
        for (int i = 0; i < size; i++) {
          floats[i] = random.nextFloat();
        }
        return floats;
      }
    };
  }

  @Override
  protected BiConsumer<Float32Decoder, float[]> consumeAndAssert() {
    return new BiConsumer<Float32Decoder, float[]>() {
      @Override
      public void accept(Float32Decoder decoder, float[] floats) {
        float[] results = decoder.nextFloats(floats.length);
        Assert.assertArrayEquals(floats, results, 0);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return this.getClass();
  }
}
