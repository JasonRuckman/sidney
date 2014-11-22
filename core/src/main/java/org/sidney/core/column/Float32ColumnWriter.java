package org.sidney.core.column;

import org.sidney.core.encoding.float32.Float32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.schema.Repetition;

public class Float32ColumnWriter extends ColumnWriter {
    private final Float32Encoder encoder;

    protected Float32ColumnWriter(Int32Encoder definitionEncoder, Int32Encoder repetitionEncoder, Repetition repetition, Float32Encoder encoder) {
        super(definitionEncoder, repetitionEncoder, repetition);
        this.encoder = encoder;
    }

    @Override
    public void writeFloat(float value) {
        currentNum++;
        encoder.writeFloat(value);
    }
}
