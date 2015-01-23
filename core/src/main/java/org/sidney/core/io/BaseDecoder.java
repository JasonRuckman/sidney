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
package org.sidney.core.io;

import org.sidney.core.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;

public abstract class BaseDecoder implements Decoder {
    private byte[] buffer;
    private int position = 0;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
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
    public void readFromStream(InputStream inputStream) throws IOException {
        setPosition(0);
        int bufferSize = Bytes.readIntFromStream(inputStream);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Expected buffer size: %s", bufferSize));
        }
        buffer = new byte[bufferSize];
        Bytes.readFully(buffer, inputStream);
    }

    protected byte readByte() {
        require(1);
        return buffer[position++];
    }

    protected boolean readBoolean() {
        require(1);
        return buffer[position++] > 0;
    }

    protected byte[] readBytesInternal(int num) {
        require(num);
        byte[] buf = new byte[num];
        System.arraycopy(getBuffer(), getPosition(), buf, 0, num);
        incrementPosition(num);
        return buf;
    }

    protected int readIntInternal() {
        require(4);

        int res = ((buffer[position + 3] & 0xff) << 24)
                | ((buffer[position + 2] & 0xff) << 16)
                | ((buffer[position + 1] & 0xff) << 8)
                | (buffer[position] & 0xff);

        position += 4;
        return res;
    }

    protected long readLongInternal() {
        require(8);
        long l = ((((long) buffer[getPosition() + 7]) << 56)
                | (((long) buffer[getPosition() + 6] & 0xff) << 48)
                | (((long) buffer[getPosition() + 5] & 0xff) << 40)
                | (((long) buffer[getPosition() + 4] & 0xff) << 32)
                | (((long) buffer[getPosition() + 3] & 0xff) << 24)
                | (((long) buffer[getPosition() + 2] & 0xff) << 16)
                | (((long) buffer[getPosition() + 1] & 0xff) << 8)
                | (((long) buffer[getPosition()] & 0xff)));
        position += 8;
        return l;
    }

    private void require(int size) {
        if (position + size > buffer.length) {
            throw new BufferUnderflowException();
        }
    }
}