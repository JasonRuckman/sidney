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
import com.github.jasonruckman.sidney.core.io.int16.ShortDecoder;
import com.github.jasonruckman.sidney.core.io.int16.ShortEncoder;
import com.github.jasonruckman.sidney.core.io.output.Output;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShortTest extends AbstractEncoderTests<ShortEncoder, ShortDecoder, short[]> {
  private final List<EncoderDecoderPair<ShortEncoder, ShortDecoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<>(
          Encoding.PLAIN.newShortEncoder(),
          Encoding.PLAIN.newShortDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.DELTABITPACKINGHYBRID.newShortEncoder(),
          Encoding.DELTABITPACKINGHYBRID.newShortDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.BITPACKED.newShortEncoder(),
          Encoding.BITPACKED.newShortDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.RLE.newShortEncoder(),
          Encoding.RLE.newShortDecoder()
      )
  );

  @Override
  protected TriConsumer<Output, ShortEncoder, short[]> encodingFunction() {
    return new TriConsumer<Output, ShortEncoder, short[]>() {
      @Override
      public void accept(Output output, ShortEncoder encoder, short[] ints) {
        if (!encoder.isDirect()) encoder.asIndirect().setOutput(output);
        for (short i : ints) {
          encoder.writeShort(i);
        }
      }
    };
  }

  @Override
  protected IntFunction<short[]> dataSupplier() {
    return new IntFunction<short[]>() {
      @Override
      public short[] apply(int size) {
        Random random = new Random(11L);
        short[] nums = new short[size];
        for (int i = 0; i < size; i++) {
          nums[i] = (short) random.nextInt(65536);
        }
        return nums;
      }
    };
  }

  @Override
  protected BiConsumer<ShortDecoder, short[]> consumeAndAssert() {
    return new BiConsumer<ShortDecoder, short[]>() {
      @Override
      public void accept(ShortDecoder decoder, short[] nums) {
        short[] ints = decoder.nextShorts(nums.length);
        Assert.assertArrayEquals(nums, ints);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<ShortEncoder, ShortDecoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return this.getClass();
  }
}
