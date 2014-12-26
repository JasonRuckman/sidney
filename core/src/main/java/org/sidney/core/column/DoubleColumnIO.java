package org.sidney.core.column;

import org.sidney.core.encoding.float64.Float64Encoder;

public class DoubleColumnIO extends ColumnIO {
    private Float64Encoder encoder;

    public DoubleColumnIO(Float64Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeDouble(double value) {
        encoder.writeDouble(value);
    }
}
