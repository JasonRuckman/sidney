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

/**
 * Utility class to simplify some common writing operations
 */
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