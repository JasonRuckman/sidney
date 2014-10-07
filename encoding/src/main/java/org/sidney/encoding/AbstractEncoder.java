package org.sidney.encoding;

import org.sidney.core.Bytes;

public abstract class AbstractEncoder implements Encoder {
    private byte[] buffer = new byte[256];
    private int position = 0;
    protected int numValues = 0;

    public byte[] getBuffer() {
        return buffer;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void reset() {
        position = 0;
        buffer = new byte[256];
    }

    protected void require(int bytes) {
        if(position + bytes >= buffer.length) {
            int newSize = Math.max(buffer.length * 2, (position + bytes) * 2);
            byte[] newBuffer = new byte[newSize];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
    }

    protected void writeBoolean(boolean value) {
        require(1);
        buffer[position++] = (byte) ((value) ? 1 : 0);
    }

    protected void writeIntLE(int value) {
        require(4);
        Bytes.writeIntOn4Bytes(value, buffer, position);
        position += 4;
    }

    protected void writeIntLE(int value, int pos) {
        Bytes.writeIntOn4Bytes(value, buffer, pos);
    }

    protected void writeLongLE(long value) {
        require(8);
        Bytes.writeLongOn8Bytes(value, buffer, position);
        position += 8;
    }
}