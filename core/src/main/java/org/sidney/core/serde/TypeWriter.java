package org.sidney.core.serde;

public class TypeWriter {
    public void writeBool(boolean value, WriteContext context) {
        context.getColumnWriter().writeBoolean(context.getIndex(), value);
    }

    public void writeByte(byte value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeShort(short value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeInt(int value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeLong(long value, WriteContext context) {
        context.getColumnWriter().writeLong(context.getIndex(), value);
    }

    public void writeFloat(float value, WriteContext context) {
        context.getColumnWriter().writeFloat(context.getIndex(), value);
    }

    public void writeDouble(double value, WriteContext context) {
        context.getColumnWriter().writeDouble(context.getIndex(), value);
    }

    public void writeBytes(byte[] value, WriteContext context) {
        context.getColumnWriter().writeBytes(context.getIndex(), value);
    }

    public void writeString(String value, WriteContext context) {
        context.getColumnWriter().writeString(context.getIndex(), value);
    }

    public <T> boolean writeNullMarker(T value, WriteContext context) {
        if (value == null) {
            context.getColumnWriter().writeNull(context.getIndex());
            return false;
        }
        context.getColumnWriter().writeNotNull(context.getIndex());
        return true;
    }

    public  <T> boolean writeNullMarkerAndType(T value, WriteContext context) {
        if (value == null) {
            context.getColumnWriter().writeNull(context.getIndex());
            return false;
        }
        context.getColumnWriter().writeConcreteType(value.getClass(), context.getIndex(), context);
        context.getColumnWriter().writeNotNull(context.getIndex());
        return true;
    }

    public void writeRepetitionCount(int index, int count, WriteContext context) {
        context.getColumnWriter().writeRepetitionCount(index, count);
    }

    private void writeIntLike(int value, WriteContext context) {
        context.getColumnWriter().writeInt(context.getIndex(), value);
    }
}