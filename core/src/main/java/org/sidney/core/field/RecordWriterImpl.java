package org.sidney.core.field;

import org.sidney.core.column.ColumnWriter;

public class RecordWriterImpl implements RecordWriter {
    private final ColumnWriter[] writers;
    private int currentIndex = 0;

    public RecordWriterImpl(ColumnWriter[] writers) {
        this.writers = writers;
    }

    @Override
    public void writeBoolean(boolean value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeBoolean(value);
    }

    @Override
    public void writeInt(int value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeInt(value);
    }

    @Override
    public void writeLong(long value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeLong(value);
    }

    @Override
    public void writeFloat(float value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeFloat(value);
    }

    @Override
    public void writeDouble(double value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeDouble(value);
    }

    @Override
    public void writeBytes(byte[] value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeBytes(value);
    }

    @Override
    public void writeString(String value) {
        writers[currentIndex].writeNotNull();
        writers[currentIndex].writeString(value);
    }

    @Override
    public void writeNull() {
        writers[currentIndex].writeNull();
    }

    @Override
    public void startField() {
        writers[currentIndex].start();
    }

    @Override
    public void endField() {
        writers[currentIndex++].end();
    }

    @Override
    public void startRecord() {
        currentIndex = 0;
    }

    @Override
    public void endRecord() {

    }
}