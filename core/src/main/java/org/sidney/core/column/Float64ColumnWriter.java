package org.sidney.core.column;

import org.sidney.core.encoding.float64.Float64Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.schema.Repetition;

public class Float64ColumnWriter extends ColumnWriter {
    private final Float64Encoder encoder;

    protected Float64ColumnWriter(Int32Encoder definitionEncoder, Int32Encoder repetitionEncoder, Repetition repetition, Float64Encoder encoder) {
        super(definitionEncoder, repetitionEncoder, repetition);
        this.encoder = encoder;
    }

    @Override
    public void writeDouble(double value) {
        currentNum++;
        encoder.writeDouble(value);
    }
}
