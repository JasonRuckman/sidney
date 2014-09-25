package org.sidney.encoding.int16;

import org.sidney.encoding.int32.FastPFORInt32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class FastPFORInt16Encoder implements Int16Encoder {
    private final FastPFORInt32Encoder delegateEncoder = new FastPFORInt32Encoder();

    @Override
    public void writeShort(short value) {
        delegateEncoder.writeInt(value);
    }

    @Override
    public void writeShorts(short[] values) {
        for (short s : values) {
            writeShort(s);
        }
    }

    @Override
    public void reset() {
        delegateEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        delegateEncoder.writeToStream(outputStream);
    }
}
