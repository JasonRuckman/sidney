package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

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
