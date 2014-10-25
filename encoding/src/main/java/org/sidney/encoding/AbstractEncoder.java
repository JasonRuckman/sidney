package org.sidney.encoding;

import com.google.common.io.LittleEndianDataOutputStream;

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
        LittleEndianDataOutputStream los = new LittleEndianDataOutputStream(outputStream);

        los.writeInt(numValues);
        los.writeInt(getPosition());
        los.write(getBuffer(), 0, getPosition());

        los.flush();
    }

    protected void ensureCapacity(int bytes) {
        if(position + bytes >= buffer.length) {
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

    protected void writeIntLE(int value) {
        ensureCapacity(4);
        Bytes.writeIntOn4Bytes(value, buffer, position);
        position += 4;
    }

    protected void writeBytesInternal(byte[] bytes, int offset, int length) {
        ensureCapacity(bytes.length);
        System.arraycopy(bytes, offset, buffer, position, length);
        position += length;
    }

    protected void writeIntLE(int value, int pos) {
        Bytes.writeIntOn4Bytes(value, buffer, pos);
    }

    protected void writeLongLE(long value) {
        ensureCapacity(8);
        Bytes.writeLongOn8Bytes(value, buffer, position);
        position += 8;
    }
}