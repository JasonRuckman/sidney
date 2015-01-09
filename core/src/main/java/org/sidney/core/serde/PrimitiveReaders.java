package org.sidney.core.serde;

import org.sidney.core.field.FieldAccessor;

public class PrimitiveReaders {
    public static abstract class PrimitiveReader {
        public Object readValue(TypeReader typeReader, ReadContext context) {
            throw new IllegalStateException();
        }
        abstract public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor);
    }

    static class BoolPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setBoolean(parent, typeReader.readBoolean(context));
        }
    }

    static class IntPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setInt(parent, typeReader.readInt(context));
        }
    }

    static class LongPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setLong(parent, typeReader.readLong(context));
        }
    }

    static class FloatPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setFloat(parent, typeReader.readFloat(context));
        }
    }

    static class DoublePrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setDouble(parent, typeReader.readDouble(context));
        }
    }

    static class BytesPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, typeReader.readBytes(context));
        }
    }

    static class StringPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, typeReader.readString(context));
        }
    }

    static class BoolRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readBoolean(context);
        }

        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, typeReader.readBoolean(context));
        }
    }
}