package org.sidney.encoding.int32;

import java.io.IOException;
import java.io.OutputStream;

public class PlainInt32Encoder implements Int32Encoder {
    private byte[] buffer = new byte[256];

    @Override
    public void writeInt(int value) {

    }

    @Override
    public void writeInts(int[] values) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {

    }

    @Override
    public String supportedEncoding() {
        return null;
    }
}
