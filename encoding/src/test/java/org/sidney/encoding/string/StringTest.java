package org.sidney.encoding.string;

import org.junit.Assert;
import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class StringTest extends AbstractEncoderTests<StringEncoder, StringDecoder, String[]> {
    private List<EncoderDecoderPair<StringEncoder, StringDecoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new PlainStringEncoder(), new PlainStringDecoder()),
        new EncoderDecoderPair<>(new DeltaLengthStringEncoder(), new DeltaLengthStringDecoder())
    );

    protected BiConsumer<StringEncoder, String[]> encodingFunction() {
        return (encoder, strings) -> encoder.writeStrings(strings);
    }

    @Override
    protected IntFunction<String[]> dataSupplier() {
        return (num) -> {
            String[] strings = new String[num];
            Random random = new Random(11L);
            for(int i = 0; i < num; i++) {
                strings[i] = new BigInteger(random.nextInt(500), random).toString();
            }
            return strings;
        };
    }

    @Override
    protected BiConsumer<StringDecoder, String[]> consumeAndAssert() {
        return (decoder, strings) -> {
            String[] results = decoder.readStrings(strings.length);
            Assert.assertArrayEquals(strings, results);
        };
    }

    @Override
    protected List<EncoderDecoderPair<StringEncoder, StringDecoder>> pairs() {
        return pairs;
    }

    @Override
    protected Class getRunningClass() {
        return StringTest.class;
    }
}
