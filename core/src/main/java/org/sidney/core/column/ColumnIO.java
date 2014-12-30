package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.bool.BoolDecoder;
import org.sidney.core.encoding.bool.BoolEncoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;

public class ColumnIO {
    private BoolEncoder definitionEncoder;
    private Int32Encoder repetitionEncoder;
    private BoolDecoder definitionDecoder;
    private Int32Decoder repetitionDecoder;

    private int currentNum = 0;
    private String path;

    public void setRepetitionEncoder(Int32Encoder repetitionEncoder) {
        this.repetitionEncoder = repetitionEncoder;
    }

    public void setDefinitionEncoder(BoolEncoder definitionEncoder) {
        this.definitionEncoder = definitionEncoder;
    }

    public void setRepetitionDecoder(Int32Decoder repetitionDecoder) {
        this.repetitionDecoder = repetitionDecoder;
    }

    public void setDefinitionDecoder(BoolDecoder definitionDecoder) {
        this.definitionDecoder = definitionDecoder;
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

    public boolean readBoolean() {
        throw new IllegalStateException();
    }

    public int readInt() {
        throw new IllegalStateException();
    }

    public long readLong() {
        throw new IllegalStateException();
    }

    public float readFloat() {
        throw new IllegalStateException();
    }

    public double readDouble() {
        throw new IllegalStateException();
    }

    public String readString() {
        throw new IllegalStateException();
    }

    public byte[] readBytes() {
        throw new IllegalStateException();
    }

    public boolean readNullMarker() {
        return definitionDecoder.nextBool();
    }

    public int readRepetitionCount() {
        return repetitionDecoder.nextInt();
    }

    public Encoder getEncoder() {
        return null;
    }

    public Decoder getDecoder() {
        return null;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ColumnIO{" +
                "path='" + path + '\'' +
                '}';
    }
}