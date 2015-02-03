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

import org.sidney.core.SidneyConf;

import java.io.IOException;
import java.io.OutputStream;

public class WriteContextImpl extends Context implements WriteContext {
    private ColumnWriter columnWriter;

    public WriteContextImpl(ColumnWriter columnWriter, SidneyConf conf) {
        super(conf);
        this.columnWriter = columnWriter;
    }

    public WriteContextImpl(ColumnWriter columnWriter, PageHeader pageHeader, SidneyConf conf) {
        super(conf);
        this.columnWriter = columnWriter;
        setPageHeader(pageHeader);
    }

    public ColumnWriter getColumnWriter() {
        return columnWriter;
    }

    public void writeBool(boolean value) {
        this.getColumnWriter().writeBoolean(this.getColumnIndex(), value);
    }

    public void writeByte(byte value) {
        writeIntLike(value);
    }

    public void writeChar(char value) {
        writeIntLike(value);
    }

    public void writeShort(short value) {
        writeIntLike(value);
    }

    public void writeInt(int value) {
        writeIntLike(value);
    }

    public void writeLong(long value) {
        this.getColumnWriter().writeLong(this.getColumnIndex(), value);
    }

    public void writeFloat(float value) {
        this.getColumnWriter().writeFloat(this.getColumnIndex(), value);
    }

    public void writeDouble(double value) {
        this.getColumnWriter().writeDouble(this.getColumnIndex(), value);
    }

    public void writeBytes(byte[] value) {
        this.getColumnWriter().writeBytes(this.getColumnIndex(), value);
    }

    public void writeString(String value) {
        this.getColumnWriter().writeString(this.getColumnIndex(), value);
    }

    public <T> boolean writeNullMarker(T value) {
        if (value == null) {
            this.getColumnWriter().writeNull(this.getColumnIndex());
            return false;
        }
        this.getColumnWriter().writeNotNull(this.getColumnIndex());
        return true;
    }

    public <T> boolean writeNullMarkerAndType(T value) {
        if (value == null) {
            this.getColumnWriter().writeNull(this.getColumnIndex());
            return false;
        }
        this.getColumnWriter().writeNotNull(this.getColumnIndex());
        this.getColumnWriter().writeConcreteType(value.getClass(), this.getColumnIndex(), this);
        return true;
    }

    public void writeRepetitionCount(int count) {
        this.getColumnWriter().writeRepetitionCount(getColumnIndex(), count);
    }

    @Override
    public void flushToOutputStream(OutputStream outputStream) throws IOException {
        this.getColumnWriter().flushToOutputStream(outputStream);
    }

    private void writeIntLike(int value) {
        this.getColumnWriter().writeInt(this.getColumnIndex(), value);
    }
}
