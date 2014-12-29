package org.sidney.core.column;

import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolEncoder;

public class BoolColumnIO extends ColumnIO {
    private final BoolEncoder boolEncoder;

    public BoolColumnIO(BoolEncoder boolEncoder) {
        this.boolEncoder = boolEncoder;
    }

    @Override
    public void writeBoolean(boolean value) {
        this.boolEncoder.writeBool(value);
    }

    @Override
    public Encoder getEncoder() {
        return boolEncoder;
    }
}
