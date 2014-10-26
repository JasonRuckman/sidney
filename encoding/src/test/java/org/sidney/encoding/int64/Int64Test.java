package org.sidney.encoding.int64;

import org.junit.Assert;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Int64Test extends AbstractEncoderTests<Int64Encoder, Int64Decoder, long[]> {
    private final List<EncoderDecoderPair<Int64Encoder, Int64Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new PlainInt64Encoder(), new PlainInt64Decoder())
    );

    @Override
    protected BiConsumer<Int64Encoder, long[]> encodingFunction() {
        return (encoder, longs) -> {
            for(long l : longs) {
                encoder.writeLong(l);
            }
        };
    }

    //TODO: make this generate blocks of different bitwidths
    @Override
    protected IntFunction<long[]> dataSupplier() {
        return (size) -> {
            Random random = new Random(11L);
            long[] arr = new long[size];
            for (int i = 0; i < size; i++) {
                arr[i] = random.nextLong();
            }
            return arr;
        };
    }

    @Override
    protected BiConsumer<Int64Decoder, long[]> consumeAndAssert() {
        return (decoder, nums) -> {
            long[] ints = decoder.nextLongs(nums.length);
            Assert.assertArrayEquals(nums, ints);
        };
    }

    @Override
    protected List<EncoderDecoderPair<Int64Encoder, Int64Decoder>> pairs() {
        return pairs;
    }

    @Override
    protected Class getRunningClass() {
        return this.getClass();
    }
}
