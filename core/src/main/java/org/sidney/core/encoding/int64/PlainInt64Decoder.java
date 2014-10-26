package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class PlainInt64Decoder extends AbstractDecoder implements Int64Decoder {
    @Override
    public long nextLong() {
        return readLongLE();
    }

    @Override
    public long[] nextLongs(int num) {
        long[] longs = new long[num];
        for(int i = 0; i < num; i++) {
            longs[i] = readLongLE();
        }
        return longs;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
