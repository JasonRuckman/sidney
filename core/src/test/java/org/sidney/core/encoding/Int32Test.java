package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.int32.BitPackingInt32Decoder;
import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.core.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int32.PlainInt32Decoder;
import org.sidney.core.encoding.int32.PlainInt32Encoder;

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
            new BitPackingInt32Encoder(),
            new BitPackingInt32Decoder()
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
                nums[i] = random.nextInt(65536);
            }
            return nums;
        };
    }

    @Override
    protected BiConsumer<Int32Decoder, int[]> consumeAndAssert() {
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
}