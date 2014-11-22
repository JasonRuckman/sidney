package org.sidney.core.column;

import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.schema.Repetition;

public class Int32ColumnWriter extends ColumnWriter {
    private final Int32Encoder encoder;

    protected Int32ColumnWriter(Int32Encoder definitionEncoder, Int32Encoder repetitionEncoder, Repetition repetition, Int32Encoder encoder) {
        super(definitionEncoder, repetitionEncoder, repetition);
        this.encoder = encoder;
    }

    @Override
    public void writeInt(int value) {
        currentNum++;
        encoder.writeInt(value);
    }
}
