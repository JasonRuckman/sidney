package org.sidney.core.encoding;

import org.sidney.core.encoding.bytes.ByteArrayDecoder;
import org.sidney.core.encoding.bytes.ByteArrayEncoder;
import org.sidney.core.encoding.bytes.BytesDecoder;
import org.sidney.core.encoding.bytes.BytesEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BytesTest extends AbstractEncoderTests<BytesEncoder, BytesDecoder, byte[]> {
    private final List<EncoderDecoderPair<BytesEncoder, BytesDecoder>> pairs = Arrays.asList(
            new EncoderDecoderPair<BytesEncoder, BytesDecoder>(new ByteArrayEncoder(), new ByteArrayDecoder())
    );

    protected BiConsumer<BytesEncoder, byte[]> encodingFunction() {
        return new BiConsumer<BytesEncoder, byte[]>() {
            @Override
            public void accept(BytesEncoder encoder, byte[] bytes) {
                encoder.writeBytes(bytes);
            }
        };
    }

    @Override
    protected IntFunction<byte[]> dataSupplier() {
        return new IntFunction<byte[]>() {
            @Override
            public byte[] apply(int size) {
                Random random = new Random(11L);
                byte[] bytes = new byte[size];
                random.nextBytes(bytes);
                return bytes;
            }
        };
    }

    @Override
    protected BiConsumer<BytesDecoder, byte[]> consumeAndAssert() {
        return new BiConsumer<BytesDecoder, byte[]>() {
            @Override
            public void accept(BytesDecoder decoder, byte[] bytes) {
                decoder.readBytes(bytes.length);
            }
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