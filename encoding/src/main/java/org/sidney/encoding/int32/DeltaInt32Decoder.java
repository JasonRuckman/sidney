package org.sidney.encoding.int32;

import java.io.IOException;
import java.io.InputStream;

public class DeltaInt32Decoder implements Int32Decoder {
    @Override
    public int nextInt() {
        return 0;
    }

    @Override
    public int[] nextInts(int num) {
        return new int[0];
    }

    @Override
    public void readFromBuffer(byte[] buffer) {

    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {

    }
}
