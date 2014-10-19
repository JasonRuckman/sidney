package org.sidney.encoding.int64;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

public class PlainInt64Encoder extends AbstractEncoder implements Int64Encoder {
    @Override
    public void writeLong(long value) {
        writeLongLE(value);
        numValues++;
    }

    @Override
    public void writeLongs(long[] values) {
        for (long l : values) {
            writeLong(l);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}