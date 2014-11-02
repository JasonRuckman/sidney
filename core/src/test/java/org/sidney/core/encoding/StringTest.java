package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.string.CharAsIntStringDecoder;
import org.sidney.core.encoding.string.CharAsIntStringEncoder;
import org.sidney.core.encoding.string.DeltaLengthStringDecoder;
import org.sidney.core.encoding.string.DeltaLengthStringEncoder;
import org.sidney.core.encoding.string.PlainStringDecoder;
import org.sidney.core.encoding.string.PlainStringEncoder;
import org.sidney.core.encoding.string.StringDecoder;
import org.sidney.core.encoding.string.StringEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class StringTest extends AbstractEncoderTests<StringEncoder, StringDecoder, String[]> {
    private List<EncoderDecoderPair<StringEncoder, StringDecoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new PlainStringEncoder(), new PlainStringDecoder()),
        new EncoderDecoderPair<>(new DeltaLengthStringEncoder(), new DeltaLengthStringDecoder()),
        new EncoderDecoderPair<>(new CharAsIntStringEncoder(), new CharAsIntStringDecoder())
    );

    protected BiConsumer<StringEncoder, String[]> encodingFunction() {
        return (encoder, strings) -> encoder.writeStrings(strings);
    }

    @Override
    protected IntFunction<String[]> dataSupplier() {
        return (num) -> {
            String[] strings = new String[num];
            Random random = new Random(11L);
            for (int i = 0; i < num; i++) {
                char[] chars = new char[124];
                for(int j = 0; j < chars.length; j++) {
                    chars[j] = (char) random.nextInt(32767);
                }
                strings[i] = new String(chars);
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
