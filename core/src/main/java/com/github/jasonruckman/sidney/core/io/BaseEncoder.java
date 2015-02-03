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
package com.github.jasonruckman.sidney.core.io;

import com.github.jasonruckman.sidney.core.Bytes;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseEncoder implements Encoder {
    private byte[] buffer = new byte[256];
    private int position = 0;

    public byte[] getBuffer() {
        return buffer;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void incrementPosition(int size) {
        position += size;
    }

    @Override
    public void reset() {
        position = 0;
        buffer = new byte[256];
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        Bytes.writeIntToStream(getPosition(), outputStream);
        outputStream.write(getBuffer(), 0, getPosition());
    }

    protected void writeBooleanToBuffer(boolean value) {
        ensureCapacity(1);
        buffer[position++] = (byte) ((value) ? 1 : 0);
    }

    protected void writeIntToBuffer(int value) {
        ensureCapacity(4);
        Bytes.writeIntOn4Bytes(value, buffer, position);
        position += 4;
    }

    protected void writeLongToBuffer(long value) {
        ensureCapacity(8);
        Bytes.writeLong(value, buffer, position);
        position += 8;
    }

    protected void writeBytesToBuffer(byte[] bytes, int offset, int length) {
        ensureCapacity(bytes.length);
        System.arraycopy(bytes, offset, buffer, position, length);
        position += length;
    }

    protected void ensureCapacity(int bytes) {
        if (position + bytes >= buffer.length) {
            int newSize = Math.max(buffer.length * 2, (position + bytes) * 2);
            byte[] newBuffer = new byte[newSize];
            System.arraycopy(buffer, 0, newBuffer, 0, position);
            buffer = newBuffer;
        }
    }
}