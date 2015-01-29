package org.sidney.core.serde;

import java.io.IOException;
import java.io.OutputStream;

public class WriteContextImpl extends Context implements WriteContext {
    private ColumnWriter columnWriter;

    public WriteContextImpl(ColumnWriter columnWriter) {
        this.columnWriter = columnWriter;
    }

    public WriteContextImpl(ColumnWriter columnWriter, PageHeader pageHeader) {
        this.columnWriter = columnWriter;
        setPageHeader(pageHeader);
    }

    public ColumnWriter getColumnWriter() {
        return columnWriter;
    }

    public void writeBool(boolean value) {
        this.getColumnWriter().writeBoolean(this.getColumnIndex(), value);
    }

    public void writeByte(byte value) {
        writeIntLike(value);
    }

    public void writeChar(char value) {
        writeIntLike(value);
    }

    public void writeShort(short value) {
        writeIntLike(value);
    }

    public void writeInt(int value) {
        writeIntLike(value);
    }

    public void writeLong(long value) {
        this.getColumnWriter().writeLong(this.getColumnIndex(), value);
    }

    public void writeFloat(float value) {
        this.getColumnWriter().writeFloat(this.getColumnIndex(), value);
    }

    public void writeDouble(double value) {
        this.getColumnWriter().writeDouble(this.getColumnIndex(), value);
    }

    public void writeBytes(byte[] value) {
        this.getColumnWriter().writeBytes(this.getColumnIndex(), value);
    }

    public void writeString(String value) {
        this.getColumnWriter().writeString(this.getColumnIndex(), value);
    }

    public <T> boolean writeNullMarker(T value) {
        if (value == null) {
            this.getColumnWriter().writeNull(this.getColumnIndex());
            return false;
        }
        this.getColumnWriter().writeNotNull(this.getColumnIndex());
        return true;
    }

    public <T> boolean writeNullMarkerAndType(T value) {
        if (value == null) {
            this.getColumnWriter().writeNull(this.getColumnIndex());
            return false;
        }
        this.getColumnWriter().writeNotNull(this.getColumnIndex());
        this.getColumnWriter().writeConcreteType(value.getClass(), this.getColumnIndex(), this);
        return true;
    }

    public void writeRepetitionCount(int index, int count) {
        this.getColumnWriter().writeRepetitionCount(index, count);
    }

    @Override
    public void flushToOutputStream(OutputStream outputStream) throws IOException {
        this.getColumnWriter().flushToOutputStream(outputStream);
    }

    private void writeIntLike(int value) {
        this.getColumnWriter().writeInt(this.getColumnIndex(), value);
    }
}
