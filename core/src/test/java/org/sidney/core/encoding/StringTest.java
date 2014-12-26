package org.sidney.core.encoding;

import org.junit.Assert;
import org.sidney.core.encoding.string.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringTest extends AbstractEncoderTests<StringEncoder, StringDecoder, String[]> {
    private List<EncoderDecoderPair<StringEncoder, StringDecoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new PlainStringEncoder(), new PlainStringDecoder()),
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new DeltaLengthStringEncoder(), new DeltaLengthStringDecoder()),
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new CharAsIntStringEncoder(), new CharAsIntStringDecoder())
    );

    protected BiConsumer<StringEncoder, String[]> encodingFunction() {
        return new BiConsumer<StringEncoder, String[]>() {
            @Override
            public void accept(StringEncoder encoder, String[] strings) {
                encoder.writeStrings(strings);
            }
        };
    }

    @Override
    protected IntFunction<String[]> dataSupplier() {
        return new IntFunction<String[]>() {
            @Override
            public String[] apply(int size) {
                String[] strings = new String[size];
                Random random = new Random(11L);
                for (int i = 0; i < size; i++) {
                    char[] chars = new char[124];
                    for (int j = 0; j < chars.length; j++) {
                        chars[j] = (char) random.nextInt(32767);
                    }
                    strings[i] = new String(chars);
                }
                return strings;
            }
        };
    }

    @Override
    protected BiConsumer<StringDecoder, String[]> consumeAndAssert() {
        return new BiConsumer<StringDecoder, String[]>() {
            @Override
            public void accept(StringDecoder decoder, String[] strings) {
                String[] results = decoder.readStrings(strings.length);
                Assert.assertArrayEquals(strings, results);
            }
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
