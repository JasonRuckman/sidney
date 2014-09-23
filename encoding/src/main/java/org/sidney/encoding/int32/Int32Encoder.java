package org.sidney.encoding.int32;

import org.sidney.encoding.Encoder;

public interface Int32Encoder extends Encoder {
    void writeInt(int value);

    void writeInts(int[] values);
}