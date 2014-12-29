package org.sidney.core.column;

import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int64.Int64Encoder;

public class LongColumnIO extends ColumnIO {
    private final Int64Encoder encoder;

    public LongColumnIO(Int64Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeLong(long value) {
        encoder.writeLong(value);
    }

    @Override
    public Encoder getEncoder() {
        return encoder;
    }
}
