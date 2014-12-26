package org.sidney.core.column;

import org.sidney.core.encoding.float32.Float32Encoder;

public class FloatColumnIO extends ColumnIO {
    private final Float32Encoder encoder;

    public FloatColumnIO(Float32Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeFloat(float value) {
        encoder.writeFloat(value);
    }
}
