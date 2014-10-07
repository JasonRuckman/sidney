package org.sidney.encoding.int64;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Bytes;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;
import org.sidney.encoding.TriConsumer;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Int64Tests extends AbstractEncoderTests<Int64Encoder, Int64Decoder, long[]> {
    private final List<EncoderDecoderPair<Int64Encoder, Int64Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new DeltaInt64Encoder(), new DeltaInt64Decoder()),
        new EncoderDecoderPair<>(new KryoInt64Encoder(1024000 * 8), new KryoInt64Decoder())
    );

    @Test
    public void runOnRandomInputs() {
        runAll();
    }

    @Override
    protected BiConsumer<Int64Encoder, long[]> encodingFunction() {
        return (encoder, longs) -> {
            for(long l : longs) {
                encoder.writeLong(l);
            }
        };
    }

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
    protected BiConsumer<Int64Decoder, long[]> dataConsumerAndAsserter() {
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
