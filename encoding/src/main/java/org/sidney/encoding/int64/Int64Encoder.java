package org.sidney.encoding.int64;

import org.sidney.encoding.Encoder;

import java.nio.ByteBuffer;

public interface Int64Encoder extends Encoder {
    void writeLong(long value);

    void writeLongs(long[] values);
}
