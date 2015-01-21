package org.sidney.core.io;

import java.io.IOException;
import java.io.OutputStream;

public class SidneyByteArrayOutputStream extends OutputStream {
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    private byte[] buffer;
    private int count = 0;

    public SidneyByteArrayOutputStream() {
        buffer = new byte[1024];
    }

    public SidneyByteArrayOutputStream(int initialSize) {
        buffer = new byte[initialSize];
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(int b) throws IOException {
        ensureCapacity(1);
        buffer[count++] = (byte) b;
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        ensureCapacity(len);
        System.arraycopy(b, off, buffer, count, len);
        count += len;
    }

    private void ensureCapacity(int size) {
        if(count + size >= buffer.length) {
            byte[] newBuffer = new byte[Math.max(count + size, buffer.length * 2)];
            System.arraycopy(buffer, 0, newBuffer, 0, count);
            buffer = newBuffer;
        }
    }
}