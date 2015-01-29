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
import org.sidney.core.JavaTypeRefBuilder;

public class Primitives {
    static abstract class PrimitiveWriter extends BaseWriter {
        public PrimitiveWriter(Registrations registrations, Class<?> type) {
            super(registrations, JavaTypeRefBuilder.typeRef(type));
        }
    }

    public static class BoolWriter extends PrimitiveWriter {
        public BoolWriter(Registrations registrations) {
            super(registrations, boolean.class);
        }

        public void writeBool(boolean value) {
            context.writeBool(value);
        }
    }

    public static class ByteWriter extends PrimitiveWriter {
        public ByteWriter(Registrations registrations) {
            super(registrations, byte.class);
        }

        public void writeByte(byte value) {
            context.writeByte(value);
        }
    }

    public static class CharWriter extends PrimitiveWriter {
        public CharWriter(Registrations registrations) {
            super(registrations, char.class);
        }

        public void writeChar(char value) {
            context.writeChar(value);
        }
    }

    public static class ShortWriter extends PrimitiveWriter {
        public ShortWriter(Registrations registrations) {
            super(registrations, short.class);
        }

        public void writeShort(short value) {
            context.writeShort(value);
        }
    }

    public static class IntWriter extends PrimitiveWriter {
        public IntWriter(Registrations registrations) {
            super(registrations, int.class);
        }

        public void writeInt(int value) {
            context.writeInt(value);
        }
    }

    public static class LongWriter extends PrimitiveWriter {
        public LongWriter(Registrations registrations) {
            super(registrations, long.class);
        }

        public void writeLong(long value) {
            context.writeLong(value);
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
            context.writeDouble(value);
        }
    }

    public static class PrimitiveReader extends BaseReader {
        public PrimitiveReader(Registrations registrations, Class<?> type) {
            super(registrations, JavaTypeRefBuilder.typeRef(type));
        }
    }

    public static class BoolReader extends PrimitiveReader {
        public BoolReader(Registrations registrations) {
            super(registrations, boolean.class);
        }

        public boolean readBoolean() {
            return context.readBoolean();
        }
    }

    public static class ByteReader extends PrimitiveReader {
        public ByteReader(Registrations registrations) {
            super(registrations, byte.class);
        }

        public byte readByte() {
            return context.readByte();
        }
    }

    public static class CharReader extends PrimitiveReader {
        public CharReader(Registrations registrations) {
            super(registrations, char.class);
        }

        public char readChar() {
            return context.readChar();
        }
    }

    public static class ShortReader extends PrimitiveReader {
        public ShortReader(Registrations registrations) {
            super(registrations, short.class);
        }

        public short readShort() {
            return context.readShort();
        }
    }

    public static class IntReader extends PrimitiveReader {
        public IntReader(Registrations registrations) {
            super(registrations, int.class);
        }

        public int readInt() {
            return context.readInt();
        }
    }

    public static class LongReader extends PrimitiveReader {
        public LongReader(Registrations registrations) {
            super(registrations, long.class);
        }

        public long readLong() {
            return context.readLong();
        }
    }

    public static class FloatReader extends PrimitiveReader {
        public FloatReader(Registrations registrations) {
            super(registrations, float.class);
        }

        public float readFloat() {
            return context.readFloat();
        }
    }

    public static class DoubleReader extends PrimitiveReader {
        public DoubleReader(Registrations registrations) {
            super(registrations, double.class);
        }

        public double readDouble() {
            return context.readDouble();
        }
    }
}