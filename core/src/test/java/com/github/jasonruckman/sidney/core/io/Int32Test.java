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

import com.github.jasonruckman.sidney.core.io.int32.*;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Int32Test extends AbstractEncoderTests<Int32Encoder, Int32Decoder, int[]> {
    private final List<EncoderDecoderPair<Int32Encoder, Int32Decoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<Int32Encoder, Int32Decoder>(
                    new Plain.PlainInt32Encoder(),
                    new Plain.PlainInt32Decoder()
            ),
            new EncoderDecoderPair<Int32Encoder, Int32Decoder>(
                    new DeltaBitPacking.DeltaBitPackingInt32Encoder(),
                    new DeltaBitPacking.DeltaBitPackingInt32Decoder()
            ),
            new EncoderDecoderPair<Int32Encoder, Int32Decoder>(
                    new BitPacking.BitPackingInt32Encoder(),
                    new BitPacking.BitPackingInt32Decoder()
            ),
            new EncoderDecoderPair<Int32Encoder, Int32Decoder>(
                    new RLE.RLEInt32Encoder(),
                    new RLE.RLEInt32Decoder()
            )
    );

    @Override
    protected BiConsumer<Int32Encoder, int[]> encodingFunction() {
        return new BiConsumer<Int32Encoder, int[]>() {
            @Override
            public void accept(Int32Encoder encoder, int[] ints) {
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