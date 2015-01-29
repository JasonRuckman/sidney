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

public class ReadContext extends Context {
    private ColumnReader columnReader;

    public ReadContext(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }

    public ColumnReader getColumnReader() {
        return columnReader;
    }

    public void setColumnReader(ColumnReader columnReader) {
        this.columnReader = columnReader;
    }

    public boolean readBoolean() {
        return this.getColumnReader().readBool(this.getColumnIndex());
    }

    public byte readByte() {
        return (byte) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public short readShort() {
        return (short) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public char readChar() {
        return (char) this.getColumnReader().readInt(this.getColumnIndex());
    }

    public int readInt() {
        return this.getColumnReader().readInt(this.getColumnIndex());
    }

    public long readLong() {
        return this.getColumnReader().readLong(this.getColumnIndex());
    }

    public float readFloat() {
        return this.getColumnReader().readFloat(this.getColumnIndex());
    }

    public double readDouble() {
        return this.getColumnReader().readDouble(this.getColumnIndex());
    }

    public byte[] readBytes() {
        return this.getColumnReader().readBytes(this.getColumnIndex());
    }

    public String readString() {
        return this.getColumnReader().readString(this.getColumnIndex());
    }

    public boolean readNullMarker() {
        return this.getColumnReader().readNullMarker(this.getColumnIndex());
    }

    public Class<?> readConcreteType() {
        return this.getColumnReader().readConcreteType(this.getColumnIndex(), this);
    }

    public int readRepetitionCount() {
        return this.getColumnReader().readRepetitionCount(this.getColumnIndex());
    }
}