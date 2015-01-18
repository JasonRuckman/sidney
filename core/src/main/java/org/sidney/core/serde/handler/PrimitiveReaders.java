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
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.TypeReader;

public class PrimitiveReaders {
    public static abstract class PrimitiveReader {
        public Object readValue(TypeReader typeReader, ReadContext context) {
            throw new IllegalStateException();
        }

        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, readValue(typeReader, context));
        }
    }

    public static class BoolPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setBoolean(parent, typeReader.readBoolean(context));
        }
    }

    public static class BytePrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setByte(parent, typeReader.readByte(context));
        }
    }

    public static class ShortPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setShort(parent, typeReader.readShort(context));
        }
    }

    public static class CharPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setChar(parent, typeReader.readChar(context));
        }
    }

    public static class IntPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setInt(parent, typeReader.readInt(context));
        }
    }

    public static class LongPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setLong(parent, typeReader.readLong(context));
        }
    }

    public static class FloatPrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setFloat(parent, typeReader.readFloat(context));
        }
    }

    public static class DoublePrimitiveReader extends PrimitiveReader {
        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.setDouble(parent, typeReader.readDouble(context));
        }
    }

    public static class BytesPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readBytes(context);
        }

        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, typeReader.readBytes(context));
        }
    }

    public static class StringPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readString(context);
        }

        @Override
        public void readIntoField(Object parent, TypeReader typeReader, ReadContext context, FieldAccessor accessor) {
            accessor.set(parent, typeReader.readString(context));
        }
    }

    public static class BoolRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readBoolean(context);
        }
    }

    public static class ByteRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readByte(context);
        }
    }

    public static class CharRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readChar(context);
        }
    }

    public static class ShortRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readShort(context);
        }
    }

    public static class IntRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readInt(context);
        }
    }

    public static class LongRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readLong(context);
        }
    }

    public static class FloatRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readFloat(context);
        }
    }

    public static class DoubleRefPrimitiveReader extends PrimitiveReader {
        @Override
        public Object readValue(TypeReader typeReader, ReadContext context) {
            return typeReader.readDouble(context);
        }
    }
}