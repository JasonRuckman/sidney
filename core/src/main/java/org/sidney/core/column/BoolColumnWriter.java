package org.sidney.core.column;

import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.schema.Repetition;

public class BoolColumnWriter extends ColumnWriter {
    private final BoolEncoder encoder;

    protected BoolColumnWriter(Int32Encoder definitionEncoder,
                               Int32Encoder repetitionEncoder,
                               Repetition repetition,
                               BoolEncoder encoder) {
        super(definitionEncoder, repetitionEncoder, repetition);
        this.encoder = encoder;
    }

    @Override
    public void writeBoolean(boolean value) {
        currentNum++;
        encoder.writeBool(value);
    }

    @Override
    public void writeBooleans(boolean[] values) {
        currentNum += values.length;
        encoder.writeBools(values);
    }
}
