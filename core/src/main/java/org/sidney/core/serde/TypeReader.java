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

public class TypeReader {
    private int count = 0;

    public boolean readBoolean(ReadContext context) {
        return context.getColumnReader().readBool(context.getColumnIndex());
    }

    public byte readByte(ReadContext context) {
        return (byte) context.getColumnReader().readInt(context.getColumnIndex());
    }

    public short readShort(ReadContext context) {
        return (short) context.getColumnReader().readInt(context.getColumnIndex());
    }

    public char readChar(ReadContext context) {
        return (char) context.getColumnReader().readInt(context.getColumnIndex());
    }

    public int readInt(ReadContext context) {
        return context.getColumnReader().readInt(context.getColumnIndex());
    }

    public long readLong(ReadContext context) {
        return context.getColumnReader().readLong(context.getColumnIndex());
    }

    public float readFloat(ReadContext context) {
        return context.getColumnReader().readFloat(context.getColumnIndex());
    }

    public double readDouble(ReadContext context) {
        return context.getColumnReader().readDouble(context.getColumnIndex());
    }

    public byte[] readBytes(ReadContext context) {
        return context.getColumnReader().readBytes(context.getColumnIndex());
    }

    public String readString(ReadContext context) {
        return context.getColumnReader().readString(context.getColumnIndex());
    }

    public boolean readNullMarker(ReadContext context) {
        return context.getColumnReader().readNullMarker(context.getColumnIndex());
    }

    public Class<?> readConcreteType(ReadContext context) {
        return context.getColumnReader().readConcreteType(context.getColumnIndex(), context);
    }

    public int readRepetitionCount(ReadContext context) {
        return context.getColumnReader().readRepetitionCount(context.getColumnIndex());
    }
}