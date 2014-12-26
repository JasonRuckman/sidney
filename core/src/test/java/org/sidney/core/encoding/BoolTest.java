package org.sidney.core.encoding;

import org.sidney.core.encoding.bool.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoolTest extends AbstractEncoderTests<BoolEncoder, BoolDecoder, boolean[]> {
    private final List<EncoderDecoderPair<BoolEncoder, BoolDecoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<BoolEncoder, BoolDecoder>(new RoaringBitmapBoolEncoder(), new RoaringBitmapBoolDecoder()),
            new EncoderDecoderPair<BoolEncoder, BoolDecoder>(new PlainBoolEncoder(), new PlainBoolDecoder()),
            new EncoderDecoderPair<BoolEncoder, BoolDecoder>(new EWAHBitmapBoolEncoder(), new EWAHBitmapBoolDecoder()),
            new EncoderDecoderPair<BoolEncoder, BoolDecoder>(new BitPackingBoolEncoder(), new BitPackingBoolDecoder())
    );

    protected BiConsumer<BoolEncoder, boolean[]> encodingFunction() {
        return new BiConsumer<BoolEncoder, boolean[]>() {
            @Override
            public void accept(BoolEncoder encoder, boolean[] bools) {
                for (boolean b : bools) {
                    encoder.writeBool(b);
                }
            }
        };
    }

    @Override
    protected IntFunction<boolean[]> dataSupplier() {
        return new IntFunction<boolean[]>() {
            @Override
            public boolean[] apply(int size) {
                Random random = new Random(11L);
                boolean[] booleans = new boolean[size];
                for (int i = 0; i < size; i++) {
                    booleans[i] = random.nextInt() % 50 == 0;
                }
                return booleans;
            }
        };
    }

    @Override
    protected BiConsumer<BoolDecoder, boolean[]> consumeAndAssert() {
        return new BiConsumer<BoolDecoder, boolean[]>() {
            @Override
            public void accept(BoolDecoder decoder, boolean[] bools) {
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
