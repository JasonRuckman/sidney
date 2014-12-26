package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.float64.Float64Decoder;
import org.sidney.core.encoding.float64.Float64Encoder;
import org.sidney.core.encoding.float64.PlainFloat64Decoder;
import org.sidney.core.encoding.float64.PlainFloat64Encoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Float64Test extends AbstractEncoderTests<Float64Encoder, Float64Decoder, double[]> {
    private final List<EncoderDecoderPair<Float64Encoder, Float64Decoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<Float64Encoder, Float64Decoder>(new PlainFloat64Encoder(), new PlainFloat64Decoder())
    );

    @Override
    protected BiConsumer<Float64Encoder, double[]> encodingFunction() {
        return new BiConsumer<Float64Encoder, double[]>() {
            @Override
            public void accept(Float64Encoder encoder, double[] nums) {
                for (double num : nums) {
                    encoder.writeDouble(num);
                }
            }
        };
    }

    @Override
    protected IntFunction<double[]> dataSupplier() {
        return new IntFunction<double[]>() {
            @Override
            public double[] apply(int size) {
                double[] doubles = new double[size];
                Random random = new Random(11L);
                for (int i = 0; i < size; i++) {
                    doubles[i] = random.nextGaussian();
                }
                return doubles;
            }
        };
    }

    @Override
    protected BiConsumer<Float64Decoder, double[]> consumeAndAssert() {
        return new BiConsumer<Float64Decoder, double[]>() {
            @Override
            public void accept(Float64Decoder decoder, double[] doubles) {
                double[] ints = decoder.nextDoubles(doubles.length);
                Assert.assertArrayEquals(doubles, ints, 0);
            }
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