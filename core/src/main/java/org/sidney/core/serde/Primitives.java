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
package org.sidney.core.serde;

import org.sidney.core.Registrations;

public class Primitives {
    static abstract class PrimitiveWriter extends BaseWriter {
        public PrimitiveWriter(Registrations registrations, Class<?> type) {
            super(registrations);
            this.context = new WriteContext(new ColumnWriter(serializerRepository.serializer(type)));
        }
    }

    public static class BoolWriter extends PrimitiveWriter {
        public BoolWriter(Registrations registrations) {
            super(registrations, boolean.class);
        }

        public void writeBool(boolean value) {
            getTypeWriter().writeBool(value, context);
        }
    }

    public static class ByteWriter extends PrimitiveWriter {
        public ByteWriter(Registrations registrations) {
            super(registrations, byte.class);
        }

        public void writeByte(byte value) {
            getTypeWriter().writeByte(value, context);
        }
    }

    public static class CharWriter extends PrimitiveWriter {
        public CharWriter(Registrations registrations) {
            super(registrations, char.class);
        }

        public void writeChar(char value) {
            getTypeWriter().writeChar(value, context);
        }
    }

    public static class ShortWriter extends PrimitiveWriter {
        public ShortWriter(Registrations registrations) {
            super(registrations, short.class);
        }

        public void writeShort(short value) {
            getTypeWriter().writeShort(value, context);
        }
    }

    public static class IntWriter extends PrimitiveWriter {
        public IntWriter(Registrations registrations) {
            super(registrations, int.class);
        }

        public void writeInt(int value) {
            getTypeWriter().writeInt(value, getContext());
        }
    }

    public static class LongWriter extends PrimitiveWriter {
        public LongWriter(Registrations registrations) {
            super(registrations, long.class);
        }

        public void writeLong(long value) {
            getTypeWriter().writeLong(value, getContext());
        }
    }

    public static class FloatWriter extends PrimitiveWriter {
        public FloatWriter(Registrations registrations) {
            super(registrations, float.class);
        }
    }

    public static class DoubleWriter extends PrimitiveWriter {
        public DoubleWriter(Registrations registrations) {
            super(registrations, double.class);
        }

        public void writeDouble(double value) {
            getTypeWriter().writeDouble(value, getContext());
        }
    }

    public static class PrimitiveReader extends BaseReader {
        public PrimitiveReader(Registrations registrations, Class<?> type) {
            super(registrations);
            this.serializer = serializerRepository.serializer(type);
        }
    }

    public static class BoolReader extends PrimitiveReader {
        public BoolReader(Registrations registrations) {
            super(registrations, boolean.class);
        }

        public boolean readBoolean() {
            return typeReader.readBoolean(context);
        }
    }

    public static class ByteReader extends PrimitiveReader {
        public ByteReader(Registrations registrations) {
            super(registrations, byte.class);
        }

        public byte readByte() {
            return typeReader.readByte(context);
        }
    }

    public static class CharReader extends PrimitiveReader {
        public CharReader(Registrations registrations) {
            super(registrations, char.class);
        }

        public char readChar() {
            return typeReader.readChar(context);
        }
    }

    public static class ShortReader extends PrimitiveReader {
        public ShortReader(Registrations registrations) {
            super(registrations, short.class);
        }

        public short readShort() {
            return typeReader.readShort(context);
        }
    }

    public static class IntReader extends PrimitiveReader {
        public IntReader(Registrations registrations) {
            super(registrations, int.class);
        }

        public int readInt() {
            return typeReader.readInt(context);
        }
    }

    public static class LongReader extends PrimitiveReader {
        public LongReader(Registrations registrations) {
            super(registrations, long.class);
        }

        public long readLong() {
            return typeReader.readLong(context);
        }
    }

    public static class FloatReader extends PrimitiveReader {
        public FloatReader(Registrations registrations) {
            super(registrations, float.class);
        }

        public float readFloat() {
            return typeReader.readFloat(context);
        }
    }

    public static class DoubleReader extends PrimitiveReader {
        public DoubleReader(Registrations registrations) {
            super(registrations, double.class);
        }

        public double readDouble() {
            return typeReader.readDouble(context);
        }
    }
}