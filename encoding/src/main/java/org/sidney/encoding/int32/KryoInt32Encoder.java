package org.sidney.encoding.int32;

import com.esotericsoftware.kryo.io.Output;

import java.io.OutputStream;

public class KryoInt32Encoder implements Int32Encoder {
    private byte[] buffer = new byte[256];
    private Output output = new Output(buffer, Integer.MAX_VALUE);

    @Override
    public void writeInt(int value) {
        output.writeInt(value);
    }

    @Override
    public void writeInts(int[] values) {
        for (int value : values) {
            writeInt(value);
        }
    }

    @Override
    public void reset() {
        output.setPosition(0);
    }

    @Override
    public int writeToBuffer(byte[] buffer, int offset) {
        System.arraycopy(output.getBuffer(), 0, buffer, offset, output.position());
        return offset + output.position();
    }

    @Override
    public void writeToStream(OutputStream outputStream) {
        output.setOutputStream(outputStream);
        output.write(buffer, 0, output.position());
    }
}