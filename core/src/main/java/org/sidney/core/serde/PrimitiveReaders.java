package org.sidney.core.serde;

import org.sidney.core.field.FieldAccessor;

public class PrimitiveReaders {
    public static abstract class PrimitiveReader {
        public Object readValue(TypeReader typeReader, ReadContext context) {
            throw new IllegalStateException();
        }
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, readValue(typeReader, context));
        }
    }

    static class BoolPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setBoolean(parent, typeReader.readBoolean(context));
        }
    }

    static class BytePrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setByte(parent, typeReader.readByte(context));
        }
    }

    static class ShortPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setShort(parent, typeReader.readShort(context));
        }
    }

    static class CharPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setChar(parent, typeReader.readChar(context));
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
    }

    static class ByteRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readByte(context);
        }
    }

    static class CharRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readChar(context);
        }
    }

    static class ShortRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readShort(context);
        }
    }

    static class IntRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readInt(context);
        }
    }

    static class LongRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readLong(context);
        }
    }

    static class FloatRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readFloat(context);
        }
    }

    static class DoubleRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readDouble(context);
        }
    }
}