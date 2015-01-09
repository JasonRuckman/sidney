package org.sidney.core.serde;

public class TypeReader {
    public boolean readBoolean(ReadContext context) {
        return context.getColumnReader().readBool(context.getIndex());
    }

    public int readInt(ReadContext context) {
        return context.getColumnReader().readInt(context.getIndex());
    }

    public long readLong(ReadContext context) {
        return context.getColumnReader().readLong(context.getIndex());
    }

    public float readFloat(ReadContext context) {
        return context.getColumnReader().readFloat(context.getIndex());
    }

    public double readDouble(ReadContext context) {
        return context.getColumnReader().readDouble(context.getIndex());
    }

    public byte[] readBytes(ReadContext context) {
        return context.getColumnReader().readBytes(context.getIndex());
    }

    public String readString(ReadContext context) {
        return context.getColumnReader().readString(context.getIndex());
    }

    public boolean readNullMarker(ReadContext context) {
        return context.getColumnReader().readNullMarker(context.getIndex());
    }

    public Class<?> readConcreteType(ReadContext context) {
        return context.getColumnReader().readConcreteType(context.getIndex(), context);
    }

    public int readRepetitionCount(ReadContext context) {
        return context.getColumnReader().readRepetitionCount(context.getIndex());
    }
}