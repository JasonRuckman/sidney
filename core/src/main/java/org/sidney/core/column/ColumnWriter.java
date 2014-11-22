package org.sidney.core.column;

import org.sidney.core.SidneyException;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.schema.Repetition;

public abstract class ColumnWriter {
    private final Int32Encoder definitionEncoder;
    private final Int32Encoder repetitionEncoder;
    private final Repetition repetition;
    protected int currentNum = 0;

    protected ColumnWriter(Int32Encoder definitionEncoder, Int32Encoder repetitionEncoder, Repetition repetition) {
        this.definitionEncoder = definitionEncoder;
        this.repetitionEncoder = repetitionEncoder;
        this.repetition = repetition;
    }

    public void writeBoolean(boolean value) {
        throw new IllegalStateException();
    }

    public void writeBooleans(boolean[] values) {
        throw new IllegalStateException();
    }

    public void writeInt(int value) {
        throw new IllegalStateException();
    }

    public void writeLong(long value) {
        throw new IllegalStateException();
    }

    public void writeFloat(float value) {
        throw new IllegalStateException();
    }

    public void writeDouble(double value) {
        throw new IllegalStateException();
    }

    public void writeBytes(byte[] value) {
        throw new IllegalStateException();
    }

    public void writeString(String value) {
        throw new IllegalStateException();
    }

    public void writeNull() {
        if(repetition != Repetition.OPTIONAL) {
            throw new SidneyException("Cannot call null on a non-optional column");
        }
        definitionEncoder.writeInt(Repetition.OPTIONAL.getValue());
    }

    public void writeNotNull() {
        if(repetition == Repetition.OPTIONAL) {
            definitionEncoder.writeInt(Repetition.REQUIRED.getValue());
        }
    }

    public void start() {
        currentNum = 0;
        if(repetition == Repetition.REPEATED) {
            startRepeated();
        }
    }

    private void startRepeated() {
        definitionEncoder.writeInt(Repetition.REPEATED.getValue());
    }

    public void end() {
        if(repetition == Repetition.REPEATED) {
            endRepeated();
        }
    }

    private void endRepeated() {
        repetitionEncoder.writeInt(currentNum);
    }
}