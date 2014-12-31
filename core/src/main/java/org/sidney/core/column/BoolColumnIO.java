package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;

import java.util.Arrays;
import java.util.List;

public class BoolColumnIO extends ColumnIO {
    private final BoolEncoder encoder;
    private final BoolDecoder decoder;

    public BoolColumnIO(BoolEncoder encoder, BoolDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeBoolean(boolean value) {
        this.encoder.writeBool(value);
    }

    @Override
    public boolean readBoolean() {
        return decoder.nextBool();
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
