package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.sidney.core.Bytes;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;
import org.sidney.encoding.TriConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Int32Test extends AbstractEncoderTests<Int32Encoder, Int32Decoder, int[]> {
    private final List<EncoderDecoderPair<Int32Encoder, Int32Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(
            new PlainInt32Encoder(),
            new PlainInt32Decoder()
        ),
        new EncoderDecoderPair<>(
            new DeltaBitPackingInt32Encoder(),
            new DeltaBitPackingInt32Decoder()
        ),
        new EncoderDecoderPair<>(
            new FastBitPackInt32Encoder(),
            new FastBitPackInt32Decoder()
        ),
        new EncoderDecoderPair<>(
            new KryoInt32Encoder(),
            new KryoInt32Decoder()
        )
    );

    @Override
    protected BiConsumer<Int32Encoder, int[]> encodingFunction() {
        return (encoder, ints) -> {
            for (int i : ints) {
                encoder.writeInt(i);
            }
        };
    }

    @Override
    protected IntFunction<int[]> dataSupplier() {
        return (size) -> {
            Random random = new Random(11L);
            int[] nums = new int[size];
            for (int i = 0; i < size; i++) {
                nums[i] = random.nextInt();
            }
            return nums;
        };
    }

    @Override
    protected BiConsumer<Int32Decoder, int[]> dataConsumerAndAsserter() {
        return (decoder, nums) -> {
            int[] ints = decoder.nextInts(nums.length);
            Assert.assertArrayEquals(nums, ints);
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

    @Test
    @Override
    public void runCompressionTests() {
        runAllWithCompression();
    }

    @Test
    @Override
    public void runTests() {
        runAllWithCompression();
    }
}