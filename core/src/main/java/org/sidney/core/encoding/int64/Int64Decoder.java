package org.sidney.core.encoding.int64;

import org.sidney.core.encoding.Decoder;

public interface Int64Decoder extends Decoder {
    long nextLong();
    long[] nextLongs(int num);
}
