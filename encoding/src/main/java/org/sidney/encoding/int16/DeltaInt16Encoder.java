package org.sidney.encoding.int16;

import org.sidney.encoding.int32.DeltaInt32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class DeltaInt16Encoder implements Int16Encoder {
    private final DeltaInt32Encoder delegateEncoder = new DeltaInt32Encoder();

    @Override
    public void writeShort(short value) {
        delegateEncoder.writeInt(value);
    }

    @Override
    public void writeShorts(short[] values) {
        for(short s : values) {
            delegateEncoder.writeInt(s);
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
