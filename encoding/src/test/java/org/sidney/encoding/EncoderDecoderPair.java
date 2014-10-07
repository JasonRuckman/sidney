package org.sidney.encoding;

import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int32.Int32Encoder;

public class EncoderDecoderPair<T extends Encoder, R extends Decoder> {
    private final T encoder;
    private final R decoder;

    public EncoderDecoderPair(T encoder, R decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public R getDecoder() {
        return decoder;
    }

    public T getEncoder() {
        return encoder;
    }
}
