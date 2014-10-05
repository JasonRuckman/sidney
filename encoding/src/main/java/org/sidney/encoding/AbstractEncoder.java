package org.sidney.encoding;

import org.sidney.core.Bytes;

public abstract class AbstractEncoder implements Encoder {
    protected byte[] buffer = new byte[256];
    private int position = 0;

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
        buffer[position++] = (byte) ((value) ? 0x1 : 0x0);
    }

    protected void writeInt(int value) {
        require(4);
        Bytes.writeIntOn4Bytes(value, buffer, position);
        position += 4;
    }

    protected void writeLong(long value) {
        require(8);
        Bytes.writeLongOn8Bytes(value, buffer, position);
        position += 8;
    }
}
