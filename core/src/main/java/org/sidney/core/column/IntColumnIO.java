package org.sidney.core.column;

import org.sidney.core.encoding.int32.Int32Encoder;

public class IntColumnIO extends ColumnIO {
    private final Int32Encoder encoder;

    public IntColumnIO(Int32Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeInt(int value) {
        encoder.writeInt(value);
    }
}