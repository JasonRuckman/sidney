package org.sidney.encoding.bool;

import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class BoolTest extends AbstractEncoderTests<BoolEncoder, BoolDecoder, boolean[]> {
    private final List<EncoderDecoderPair<BoolEncoder, BoolDecoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new RoaringBitmapBoolEncoder(), new RoaringBitmapBoolDecoder()),
        new EncoderDecoderPair<>(new PlainBoolEncoder(), new PlainBoolDecoder()),
        new EncoderDecoderPair<>(new EWAHBitmapBoolEncoder(), new EWAHBitmapBoolDecoder()),
        new EncoderDecoderPair<>(new BitPackingBoolEncoder(), new BitPackingBoolDecoder())
    );

    protected BiConsumer<BoolEncoder, boolean[]> encodingFunction() {
        return (encoder, bools) -> {
            for (boolean b : bools) {
                encoder.writeBool(b);
            }
        };
    }

    @Override
    protected IntFunction<boolean[]> dataSupplier() {
        return (size) -> {
            Random random = new Random(11L);
            boolean[] booleans = new boolean[size];
            for (int i = 0; i < size; i++) {
                booleans[i] = random.nextInt() % 500 == 0;
            }
            return booleans;
        };
    }

    @Override
    protected BiConsumer<BoolDecoder, boolean[]> consumeAndAssert() {
        return (decoder, bools) -> {
            boolean[] results = decoder.nextBools(bools.length);

            for (int i = 0; i < results.length; i++) {
                boolean left = results[i];
                boolean right = bools[i];

                if (left != right) {
                    throw new AssertionError(
                        String.format("Differed at index %s, expected %s and actual %s", i, left, right)
                    );
                }
            }
        };
    }

    @Override
    protected List<EncoderDecoderPair<BoolEncoder, BoolDecoder>> pairs() {
        return pairs;
    }

    @Override
    protected Class getRunningClass() {
        return this.getClass();
    }
}
