package org.sidney.encoding.bytes;

import org.sidney.encoding.AbstractEncoderTests;
import org.sidney.encoding.EncoderDecoderPair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public class BytesTest extends AbstractEncoderTests<BytesEncoder, BytesDecoder, byte[]> {
    private final List<EncoderDecoderPair<BytesEncoder, BytesDecoder>> pairs = Arrays.asList(
        new EncoderDecoderPair<>(new ByteArrayEncoder(), new ByteArrayDecoder())
    );
    protected BiConsumer<BytesEncoder, byte[]> encodingFunction() {
        return (encoder, bytes) -> encoder.writeBytes(bytes);
    }

    @Override
    protected IntFunction<byte[]> dataSupplier() {
        return (size) -> {
            Random random = new Random(11L);
            byte[] bytes = new byte[size];
            random.nextBytes(bytes);
            return bytes;
        };
    }

    @Override
    protected BiConsumer<BytesDecoder, byte[]> consumeAndAssert() {
        return (decoder, bytes) -> {
            decoder.readBytes(bytes.length);
        };
    }

    @Override
    protected List<EncoderDecoderPair<BytesEncoder, BytesDecoder>> pairs() {
        return pairs;
    }

    @Override
    protected Class getRunningClass() {
        return BytesTest.class;
    }
}