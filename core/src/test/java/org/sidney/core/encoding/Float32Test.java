package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.float32.Float32Decoder;
import org.sidney.core.encoding.float32.Float32Encoder;
import org.sidney.core.encoding.float32.PlainFloat32Decoder;
import org.sidney.core.encoding.float32.PlainFloat32Encoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class Float32Test extends AbstractEncoderTests<Float32Encoder, Float32Decoder, float[]> {
    private final List<EncoderDecoderPair<Float32Encoder, Float32Decoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new PlainFloat32Encoder(), new PlainFloat32Decoder())
    );

    protected BiConsumer<Float32Encoder, float[]> encodingFunction() {
        return (encoder, floats) -> {
            for (float f : floats) {
                encoder.writeFloat(f);
            }
        };
    }

    @Override
    protected IntFunction<float[]> dataSupplier() {
        return (size) -> {
            float[] floats = new float[size];
            Random random = new Random(11L);
            for (int i = 0; i < size; i++) {
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
}
