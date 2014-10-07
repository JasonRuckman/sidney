package org.sidney.encoding.bool;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;
import org.sidney.encoding.TriConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class BoolTests extends AbstractEncoderTests<BoolEncoder, BoolDecoder, boolean[]> {
    private final List<EncoderDecoderPair<BoolEncoder, BoolDecoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new EWAHBoolEncoder(), new EWAHBoolDecoder()),
        new EncoderDecoderPair<>(new PlainBoolEncoder(), new PlainBoolDecoder())
    );

    @Test
    public void runOnRandomInputs() {
        runAll();
    }

    protected BiConsumer<BoolEncoder, boolean[]> encodingFunction() {
        return (encoder, bools) -> {
            for(boolean b : bools) {
                encoder.writeBool(b);
            }
        };
    }

    @Override
    protected IntFunction<boolean[]> dataSupplier() {
        return (size) -> {
            Random random = new Random(11L);
            boolean[] booleans = new boolean[size];
            for(int i = 0; i < size; i++) {
                booleans[i] = random.nextBoolean();
            }
            return booleans;
        };
    }

    @Override
    protected BiConsumer<BoolDecoder, boolean[]> dataConsumerAndAsserter() {
        return (decoder, bools) -> {
            boolean[] results = decoder.nextBools(bools.length);
            Assert.assertArrayEquals(bools, results);
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
