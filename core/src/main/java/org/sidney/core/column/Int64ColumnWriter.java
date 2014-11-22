package org.sidney.core.column;

import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int64.Int64Encoder;
import org.sidney.core.schema.Repetition;

public class Int64ColumnWriter extends ColumnWriter {
    private final Int64Encoder encoder;

    protected Int64ColumnWriter(Int32Encoder definitionEncoder, Int32Encoder repetitionEncoder, Repetition repetition, Int64Encoder encoder) {
        super(definitionEncoder, repetitionEncoder, repetition);
        this.encoder = encoder;
    }

    @Override
    public void writeLong(long value) {
        currentNum++;
        encoder.writeLong(value);
    }
}