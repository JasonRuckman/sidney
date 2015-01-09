package org.sidney.core.serde;

import org.sidney.core.field.FieldAccessor;

public class PrimitiveWriters {
    static abstract class PrimitiveWriter {
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            throw new IllegalStateException();
        }
        public abstract void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor);
    }

    static class BoolWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeBool(accessor.getBoolean(parent), context);
        }
    }

    static class IntWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeInt(accessor.getInt(parent), context);
        }
    }

    static class LongWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeLong(accessor.getLong(parent), context);
        }
    }

    static class FloatWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeFloat(accessor.getFloat(parent), context);
        }
    }

    static class DoubleWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeDouble(accessor.getDouble(parent), context);
        }
    }

    static class StringWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeString((String)value, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeString((String) accessor.get(parent), context);
        }
    }

    static class BytesWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeBytes((byte[])value, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeBytes((byte[]) accessor.get(parent), context);
        }
    }

    static class BoolRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeBool((Boolean) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeBool((Boolean)accessor.get(parent), typeWriter, context);
        }

        private void writeBool(Boolean value, TypeWriter typeWriter, WriteContext context) {
            if(typeWriter.writeNullMarker(value, context)) {
                typeWriter.writeBool(value, context);
            }
            context.incrementIndex();
        }
    }

    static class IntRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeInteger((Integer) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeInteger((Integer) accessor.get(parent), typeWriter, context);
        }

        private void writeInteger(Integer value, TypeWriter typeWriter, WriteContext context) {
            if(typeWriter.writeNullMarker(value, context)) {
                typeWriter.writeInt(value, context);
            }
            context.incrementIndex();
        }
    }

    static class LongRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeLong((Long) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeLong((Long) accessor.get(parent), typeWriter, context);
        }

        private void writeLong(Long value, TypeWriter typeWriter, WriteContext context) {
            if(typeWriter.writeNullMarker(value, context)) {
                typeWriter.writeLong(value, context);
            }
            context.incrementIndex();
        }
    }

    static class FloatRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeFloat((Float) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeFloat((Float) accessor.get(parent), typeWriter, context);
        }

        private void writeFloat(Float value, TypeWriter typeWriter, WriteContext context) {
            if(typeWriter.writeNullMarker(value, context)) {
                typeWriter.writeFloat(value, context);
            }
            context.incrementIndex();
        }
    }

    static class DoubleRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeDouble((Double) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeDouble((Double) accessor.get(parent), typeWriter, context);
        }

        private void writeDouble(Double value, TypeWriter typeWriter, WriteContext context) {
            if(typeWriter.writeNullMarker(value, context)) {
                typeWriter.writeDouble(value, context);
            }
            context.incrementIndex();
        }
    }
}
