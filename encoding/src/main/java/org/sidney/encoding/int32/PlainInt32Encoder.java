package org.sidney.encoding.int32;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

public class PlainInt32Encoder extends AbstractEncoder implements Int32Encoder {
    @Override
    public void writeInt(int value) {
        writeIntInternal(value);
        numValues++;
    }

    @Override
    public void writeInts(int[] values) {
        for (int value : values) {
            writeInt(value);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
