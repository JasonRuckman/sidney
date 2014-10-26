package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.Encoder;

public interface Int64Encoder extends Encoder {
    void writeLong(long value);

    void writeLongs(long[] values);
}
