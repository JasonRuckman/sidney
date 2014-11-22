package org.sidney.core.field;

public interface RecordWriter {
    void writeBoolean(boolean value);
    void writeInt(int value);
    void writeLong(long value);
    void writeFloat(float value);
    void writeDouble(double value);
    void writeBytes(byte[] value);
    void writeString(String value);
    void writeNull();
    void startField();
    void endField();
    void startRecord();
    void endRecord();
}