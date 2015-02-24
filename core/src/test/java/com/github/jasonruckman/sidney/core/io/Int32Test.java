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
import com.github.jasonruckman.sidney.core.io.int32.*;
import com.github.jasonruckman.sidney.core.io.output.Output;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Int32Test extends AbstractEncoderTests<Int32Encoder, Int32Decoder, int[]> {
  private final List<EncoderDecoderPair<Int32Encoder, Int32Decoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<>(
          Encoding.PLAIN.newInt32Encoder(),
          Encoding.PLAIN.newInt32Decoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.DELTABITPACKINGHYBRID.newInt32Encoder(),
          Encoding.DELTABITPACKINGHYBRID.newInt32Decoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.BITPACKED.newInt32Encoder(),
          Encoding.BITPACKED.newInt32Decoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.RLE.newInt32Encoder(),
          Encoding.RLE.newInt32Decoder()
      )
  );

  @Override
  protected TriConsumer<Output, Int32Encoder, int[]> encodingFunction() {
    return new TriConsumer<Output, Int32Encoder, int[]>() {
      @Override
      public void accept(Output output, Int32Encoder encoder, int[] ints) {
        if(!encoder.isDirect()) encoder.asIndirect().setOutput(output);
        for (int i : ints) {
          encoder.writeInt(i);
        }
      }
    };
  }

  @Override
  protected IntFunction<int[]> dataSupplier() {
    return new IntFunction<int[]>() {
      @Override
      public int[] apply(int size) {
        Random random = new Random(11L);
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
          nums[i] = random.nextInt(65536);
        }
        return nums;
      }
    };
  }

  @Override
  protected BiConsumer<Int32Decoder, int[]> consumeAndAssert() {
    return new BiConsumer<Int32Decoder, int[]>() {
      @Override
      public void accept(Int32Decoder decoder, int[] nums) {
        int[] ints = decoder.nextInts(nums.length);
        Assert.assertArrayEquals(nums, ints);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<Int32Encoder, Int32Decoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return this.getClass();
  }
}