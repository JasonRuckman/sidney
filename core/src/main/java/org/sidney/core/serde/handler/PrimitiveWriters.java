/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.serde.handler;

import org.sidney.core.field.FieldAccessor;
import org.sidney.core.serde.TypeWriter;
import org.sidney.core.serde.WriteContext;

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

    static class ByteWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeInt(accessor.getByte(parent), context);
        }
    }

    static class ShortWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeInt(accessor.getShort(parent), context);
        }
    }

    static class CharWriter extends PrimitiveWriter {
        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeInt(accessor.getChar(parent), context);
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
            typeWriter.writeString((String) value, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            typeWriter.writeString((String) accessor.get(parent), context);
        }
    }

    static class BytesWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeBytes((byte[]) value, context);
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
            writeBool((Boolean) accessor.get(parent), typeWriter, context);
        }

        private void writeBool(Boolean value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeBool(value, context);
        }
    }

    static class ByteRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeByte((Byte) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeByte((Byte) accessor.get(parent), typeWriter, context);
        }

        private void writeByte(Byte value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeInt(value, context);
        }
    }

    static class ShortRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeShort((Short) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeShort((Short) accessor.get(parent), typeWriter, context);
        }

        private void writeShort(Short value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeInt(value, context);
        }
    }

    static class CharRefWriter extends PrimitiveWriter {
        @Override
        public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
            writeChar((Character) value, typeWriter, context);
        }

        @Override
        public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context, FieldAccessor accessor) {
            writeChar((Character) accessor.get(parent), typeWriter, context);
        }

        private void writeChar(Character value, TypeWriter typeWriter, WriteContext context) {
            typeWriter.writeInt(value, context);
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
            typeWriter.writeInt(value, context);
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
            typeWriter.writeLong(value, context);
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
            typeWriter.writeFloat(value, context);
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
            typeWriter.writeDouble(value, context);
        }
    }
}