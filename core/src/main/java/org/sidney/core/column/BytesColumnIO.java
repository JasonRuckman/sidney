package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bytes.BytesDecoder;
import org.sidney.core.encoding.bytes.BytesEncoder;

import java.util.Arrays;
import java.util.List;

public class BytesColumnIO extends ColumnIO {
    private final BytesEncoder encoder;
    private final BytesDecoder decoder;

    public BytesColumnIO(BytesEncoder encoder, BytesDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeBytes(byte[] bytes) {
        this.encoder.writeBytes(bytes);
    }

    @Override
    public byte[] readBytes() {
        //fix this call, the arg is ignored
        return decoder.readBytes(0);
    }

    @Override
    public List<Encoder> getEncoders() {
        return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
        return Arrays.asList((Decoder) decoder);
    }
}
