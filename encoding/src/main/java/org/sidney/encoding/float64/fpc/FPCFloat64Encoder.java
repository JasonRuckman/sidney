package org.sidney.encoding.float64.fpc;

import org.sidney.encoding.float64.Float64Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class FPCFloat64Encoder implements Float64Encoder {
    private byte[] buf = new byte[256];

    @Override
    public void writeDouble(double value) {

    }

    @Override
    public void writeDoubles(double[] values) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {

    }

    private void ensureCapacity(int size) {
        if(size >= buf.length) {
            byte[] newBuf = new byte[size * 2];
            System.arraycopy(buf, 0, newBuf, 0, buf.length);
            buf = newBuf;
        }
    }
}
