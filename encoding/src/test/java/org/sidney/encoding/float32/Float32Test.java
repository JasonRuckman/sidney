package org.sidney.encoding.float32;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Float32Test extends AbstractEncoderTests<Float32Encoder, Float32Decoder, float[]> {
    private final List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new KryoFloat32Encoder(), new KryoFloat32Decoder()),
        new EncoderDecoderPair<>(new ExponentMantissaPackingFloat32Encoder(), new ExponentMantissaPackingFloat32Decoder())
    );

    protected BiConsumer<Float32Encoder, float[]> encodingFunction() {
        return (encoder, floats) -> {
            for(float f : floats) {
                encoder.writeFloat(f);
            }
        };
    }

    @Override
    protected IntFunction<float[]> dataSupplier() {
        return (size) -> {
            float[] floats = new float[size];
            Random random = new Random(11L);
            for(int i = 0; i < size; i++) {
                floats[i] = random.nextFloat();
            }
            return floats;
        };
    }

    @Override
    protected BiConsumer<Float32Decoder, float[]> consumeAndAssert() {
        return (decoder, floats) -> {
            float[] results = decoder.nextFloats(floats.length);
            Assert.assertArrayEquals(floats, results, 0);
        };
    }

    @Override
    protected List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs() {
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
        runAll();
    }
}
