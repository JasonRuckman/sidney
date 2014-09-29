package org.sidney.encoding.int64;

import org.sidney.encoding.Decoder;

public interface Int64Decoder extends Decoder {
    long nextLong();
    long[] nextLongs(int num);
}
