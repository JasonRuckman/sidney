package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;

public class BoolColumnIO extends ColumnIO {
    private final BoolEncoder boolEncoder;
    private final BoolDecoder boolDecoder;

    public BoolColumnIO(BoolEncoder boolEncoder, BoolDecoder boolDecoder) {
        this.boolEncoder = boolEncoder;
        this.boolDecoder = boolDecoder;
    }

    @Override
    public void writeBoolean(boolean value) {
        this.boolEncoder.writeBool(value);
    }

    @Override
    public boolean readBoolean() {
        return boolDecoder.nextBool();
    }

    @Override
    public Encoder getEncoder() {
        return boolEncoder;
    }

    @Override
    public Decoder getDecoder() {
        return boolDecoder;
    }
}
