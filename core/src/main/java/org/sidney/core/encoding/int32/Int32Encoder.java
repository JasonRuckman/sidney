package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.Encoder;

public interface Int32Encoder extends Encoder {
    void writeInt(int value);

    void writeInts(int[] values);
}