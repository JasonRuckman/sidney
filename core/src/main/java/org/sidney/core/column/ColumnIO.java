package org.sidney.core.column;

import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Encoder;

public class ColumnIO {
    private BoolEncoder definitionEncoder;
    private Int32Encoder repetitionEncoder;
    private int currentNum = 0;

    public void setRepetitionEncoder(Int32Encoder repetitionEncoder) {
        this.repetitionEncoder = repetitionEncoder;
    }

    public void setDefinitionEncoder(BoolEncoder definitionEncoder) {
        this.definitionEncoder = definitionEncoder;
    }

    public void writeBoolean(boolean value) {
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

    public void writeBytes(byte[] bytes) {
        throw new IllegalStateException();
    }

    public void writeString(String value) {
        throw new IllegalStateException();
    }

    public void writeNotNull() {
        definitionEncoder.writeBool(true);
    }

    public ColumnIO getChild(int index) {
        throw new IllegalStateException();
    }

    public void writeNull() {
        definitionEncoder.writeBool(false);
    }

    public void startRepetition() {
        currentNum = 0;
    }

    public void endRepetition() {
        repetitionEncoder.writeInt(currentNum);
    }

    public Encoder getEncoder() {
        return null;
    }
}