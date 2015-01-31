package org.sidney.core.serde;

import java.io.IOException;
import java.io.InputStream;

public class ReadContextImpl extends Context implements ReadContext {
    private ColumnReader columnReader;

    public ReadContextImpl(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }

    public ColumnReader getColumnReader() {
        return columnReader;
    }

    public void setColumnReader(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }

    @Override
    public void loadFromInputStream(InputStream inputStream) throws IOException {
        columnReader.loadFromInputStream(inputStream);
    }

    public boolean readBoolean() {
        return this.getColumnReader().readBool(this.getColumnIndex());
    }

    public byte readByte() {
        return (byte) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public short readShort() {
        return (short) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public char readChar() {
        return (char) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public int readInt() {
        return this.getColumnReader().readInt(this.getColumnIndex());
    }

    public long readLong() {
        return this.getColumnReader().readLong(this.getColumnIndex());
    }

    public float readFloat() {
        return this.getColumnReader().readFloat(this.getColumnIndex());
    }

    public double readDouble() {
        return this.getColumnReader().readDouble(this.getColumnIndex());
    }

    public byte[] readBytes() {
        return this.getColumnReader().readBytes(this.getColumnIndex());
    }

    public String readString() {
        return this.getColumnReader().readString(this.getColumnIndex());
    }

    public boolean readNullMarker() {
        return this.getColumnReader().readNullMarker(this.getColumnIndex());
    }

    public Class<?> readConcreteType() {
        return this.getColumnReader().readConcreteType(this.getColumnIndex(), this);
    }

    public int readRepetitionCount() {
        return this.getColumnReader().readRepetitionCount(this.getColumnIndex());
    }
}
