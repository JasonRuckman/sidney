/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.io;

import org.junit.Assert;
import org.sidney.core.io.string.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringTest extends AbstractEncoderTests<StringEncoder, StringDecoder, String[]> {
    private List<EncoderDecoderPair<StringEncoder, StringDecoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new Plain.PlainStringEncoder(), new Plain.PlainStringDecoder()),
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new DeltaLength.DeltaLengthStringEncoder(), new DeltaLength.DeltaLengthStringDecoder()),
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new CharAsInt.CharAsIntStringEncoder(), new CharAsInt.CharAsIntStringDecoder()),
            new EncoderDecoderPair<StringEncoder, StringDecoder>(new RLE.RLEStringEncoder(), new RLE.RLEStringDecoder())
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
                    char[] chars = new char[random.nextInt(24)];
                    for (int j = 0; j < chars.length; j++) {
                        chars[j] = (char) random.nextInt(255);
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
