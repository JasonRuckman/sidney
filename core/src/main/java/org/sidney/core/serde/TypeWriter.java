package org.sidney.core.serde;

public class TypeWriter {
    public void writeBool(boolean value, WriteContext context) {
        context.getColumnWriter().writeBoolean(context.getColumnIndex(), value);
    }

    public void writeByte(byte value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeChar(char value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeShort(short value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeInt(int value, WriteContext context) {
        writeIntLike(value, context);
    }

    public void writeLong(long value, WriteContext context) {
        context.getColumnWriter().writeLong(context.getColumnIndex(), value);
    }

    public void writeFloat(float value, WriteContext context) {
        context.getColumnWriter().writeFloat(context.getColumnIndex(), value);
    }

    public void writeDouble(double value, WriteContext context) {
        context.getColumnWriter().writeDouble(context.getColumnIndex(), value);
    }

    public void writeBytes(byte[] value, WriteContext context) {
        context.getColumnWriter().writeBytes(context.getColumnIndex(), value);
    }

    public void writeString(String value, WriteContext context) {
        context.getColumnWriter().writeString(context.getColumnIndex(), value);
    }

    public <T> boolean writeNullMarker(T value, WriteContext context) {
        if (value == null) {
            context.getColumnWriter().writeNull(context.getColumnIndex());
            return false;
        }
        context.getColumnWriter().writeNotNull(context.getColumnIndex());
        return true;
    }

    public <T> boolean writeNullMarkerAndType(T value, WriteContext context) {
        if (value == null) {
            context.getColumnWriter().writeNull(context.getColumnIndex());
            return false;
        }
        context.getColumnWriter().writeNotNull(context.getColumnIndex());
        context.getColumnWriter().writeConcreteType(value.getClass(), context.getColumnIndex(), context);
        return true;
    }

    public void writeRepetitionCount(int index, int count, WriteContext context) {
        context.getColumnWriter().writeRepetitionCount(index, count);
    }

    private void writeIntLike(int value, WriteContext context) {
        context.getColumnWriter().writeInt(context.getColumnIndex(), value);
    }
}