package org.sidney.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * A unsafe input stream that allows access to all the internals and doesn't check anything
 */
public class SidneyByteArrayInputStream extends InputStream {
    private int count = 0;
    private byte[] buffer;

    public SidneyByteArrayInputStream(byte[] buffer) {
        this.buffer = buffer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    @Override
    public int read() throws IOException {
        return (count < buffer.length) ? (buffer[count++] & 0xff) : -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        System.arraycopy(buffer, count, b, off, len);
        count += len;
        return len;
    }
}