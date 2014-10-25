package org.sidney.core.field.writer;

public interface RecordWriter {
    void writeNull();
    void writeBoolean(boolean value);
    void writeInt(int value);
    void writeLong(long value);
    void writeFloat(float value);
    void writeDouble(double value);
    void writeBytes(byte[] value);
    void writeString(String value);
}
