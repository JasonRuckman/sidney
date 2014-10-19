package org.sidney.encoding;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.BufferUnderflowException;

public abstract class AbstractDecoder implements Decoder {
    private byte[] buffer;
    private int position = 0;
    private int numValues = 0;

    public int getNumValues() {
        return numValues;
    }

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
        inputStream = wrapStreamIfNecessary(inputStream);

        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(inputStream);

        position = 0;
        numValues = dis.readInt();
        int bufferSize = dis.readInt();

        buffer = new byte[bufferSize];
        dis.read(buffer);
    }

    protected InputStream wrapStreamIfNecessary(InputStream inputStream) {
        return (inputStream instanceof BufferedInputStream) ? inputStream : new BufferedInputStream(inputStream);
    }

    protected byte readByte() {
        require(1);
        return buffer[position++];
    }

    protected boolean readBoolean() {
        require(1);
        return buffer[position++] > 0;
    }

    protected int readIntLE() {
        require(4);

        int res = ((buffer[position + 3] & 0xff) << 24)
            | ((buffer[position + 2] & 0xff) << 16)
            | ((buffer[position + 1] & 0xff) << 8)
            | (buffer[position] & 0xff);

        position += 4;
        return res;
    }

    protected long readLongLE() {
        require(8);
        long res =
            ((long)(buffer[position + 7] & 0xff) << 56) |
            ((long)(buffer[position + 6] & 0xff) << 48) |
            ((long)(buffer[position + 5] & 0xff) << 40) |
            ((long)(buffer[position + 4] & 0xff) << 32) |
            ((long)(buffer[position + 3] & 0xff) << 24) |
            ((long)(buffer[position + 2] & 0xff) << 16) |
            ((long)(buffer[position + 1] & 0xff) << 8) |
            (long)(buffer[position] & 0xff);
        position += 8;
        return res;
    }

    protected void ensureSize(int size) {
        if(buffer == null || buffer.length < size) {
            buffer = new byte[size];
        }
    }

    private void require(int size) {
        if (position + size > buffer.length) {
            throw new BufferUnderflowException();
        }
    }
}