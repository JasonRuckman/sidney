package org.sidney.encoding.int32;

import org.sidney.core.unsafe.UnsafeBytes;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class PlainInt32Decoder extends AbstractDecoder implements Int32Decoder {
    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public int nextInt() {
        return readIntLE();
    }

    @Override
    public int[] nextInts(int num) {
        int[] results = new int[num];
        for(int i = 0; i < num; i++) {
            results[i] = nextInt();
        }
        return results;
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        super.readFromStream(inputStream);
    }
}
