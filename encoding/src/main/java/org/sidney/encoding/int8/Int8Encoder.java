package org.sidney.encoding.int8;

import org.sidney.encoding.Encoder;

public interface Int8Encoder extends Encoder {
    void writeByte(byte value);

    void writeBytes(byte[] values);
}