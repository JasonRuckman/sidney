package org.sidney.encoding.int16;

import org.sidney.encoding.Encoder;

public interface Int16Encoder extends Encoder {
    void writeShort(short value);

    void writeShorts(short[] values);
}