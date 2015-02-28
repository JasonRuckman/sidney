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
import com.github.jasonruckman.sidney.core.io.character.CharDecoder;
import com.github.jasonruckman.sidney.core.io.character.CharEncoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;
import com.github.jasonruckman.sidney.core.io.output.Output;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CharTest extends AbstractEncoderTests<CharEncoder, CharDecoder, char[]> {
  private final List<EncoderDecoderPair<CharEncoder, CharDecoder>> pairs = Arrays.asList(
      new EncoderDecoderPair<>(
          Encoding.PLAIN.newCharEncoder(),
          Encoding.PLAIN.newCharDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.DELTABITPACKINGHYBRID.newCharEncoder(),
          Encoding.DELTABITPACKINGHYBRID.newCharDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.BITPACKED.newCharEncoder(),
          Encoding.BITPACKED.newCharDecoder()
      ),
      new EncoderDecoderPair<>(
          Encoding.RLE.newCharEncoder(),
          Encoding.RLE.newCharDecoder()
      )
  );

  @Override
  protected TriConsumer<Output, CharEncoder, char[]> encodingFunction() {
    return new TriConsumer<Output, CharEncoder, char[]>() {
      @Override
      public void accept(Output output, CharEncoder encoder, char[] ints) {
        if(!encoder.isDirect()) encoder.asIndirect().setOutput(output);
        for (char i : ints) {
          encoder.writeChar(i);
        }
      }
    };
  }

  @Override
  protected IntFunction<char[]> dataSupplier() {
    return new IntFunction<char[]>() {
      @Override
      public char[] apply(int size) {
        Random random = new Random(11L);
        char[] nums = new char[size];
        for (int i = 0; i < size; i++) {
          nums[i] = (char) random.nextInt(65536);
        }
        return nums;
      }
    };
  }

  @Override
  protected BiConsumer<CharDecoder, char[]> consumeAndAssert() {
    return new BiConsumer<CharDecoder, char[]>() {
      @Override
      public void accept(CharDecoder decoder, char[] nums) {
        char[] ints = decoder.nextChars(nums.length);
        Assert.assertArrayEquals(nums, ints);
      }
    };
  }

  @Override
  protected List<EncoderDecoderPair<CharEncoder, CharDecoder>> pairs() {
    return pairs;
  }

  @Override
  protected Class getRunningClass() {
    return this.getClass();
  }
}
