package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.float64.ExpPackingFloat64Decoder;
import org.sidney.core.encoding.float64.ExpPackingFloat64Encoder;
import org.sidney.core.encoding.float64.Float64Decoder;
import org.sidney.core.encoding.float64.Float64Encoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Float64Test extends AbstractEncoderTests<Float64Encoder, Float64Decoder, double[]> {
    private final List<EncoderDecoderPair<Float64Encoder, Float64Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(
            new ExpPackingFloat64Encoder(), new ExpPackingFloat64Decoder()
        )
    );

    @Override
    protected BiConsumer<Float64Encoder, double[]> encodingFunction() {
        return (encoder, nums) -> {
            for (double i : nums) {
                encoder.writeDouble(i);
            }
        };
    }

    @Override
    protected IntFunction<double[]> dataSupplier() {
        return (size) -> {
            double[] doubles = new double[size];
            Random random = new Random(11L);
            for (int i = 0; i < size; i++) {
                doubles[i] = random.nextDouble();
            }
            return doubles;
        };
    }

    @Override
    protected BiConsumer<Float64Decoder, double[]> consumeAndAssert() {
        return (decoder, doubles) -> {
            double[] ints = decoder.nextDoubles(doubles.length);
            Assert.assertArrayEquals(doubles, ints, 0);
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