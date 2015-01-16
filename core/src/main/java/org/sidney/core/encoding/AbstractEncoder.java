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
package org.sidney.core.encoding;

import org.sidney.core.Bytes;
import org.sidney.core.encoding.io.StreamUtils;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractEncoder implements Encoder {
    protected int numValues = 0;
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
        numValues = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        StreamUtils.writeIntToStream(getPosition(), outputStream);
        outputStream.write(getBuffer(), 0, getPosition());
    }

    protected void ensureCapacity(int bytes) {
        if (position + bytes >= buffer.length) {
            int newSize = Math.max(buffer.length * 2, (position + bytes) * 2);
            byte[] newBuffer = new byte[newSize];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
    }

    protected void writeBoolean(boolean value) {
        ensureCapacity(1);
        buffer[position++] = (byte) ((value) ? 1 : 0);
    }

    protected void writeIntInternal(int value) {
        ensureCapacity(4);
        Bytes.writeIntOn4Bytes(value, buffer, position);
        position += 4;
    }

    protected void writeBytesInternal(byte[] bytes, int offset, int length) {
        ensureCapacity(bytes.length);
        System.arraycopy(bytes, offset, buffer, position, length);
        position += length;
    }

    protected void writeLongInternal(long value) {
        ensureCapacity(8);
        Bytes.writeLongOn8Bytes(value, buffer, position);
        position += 8;
    }
}