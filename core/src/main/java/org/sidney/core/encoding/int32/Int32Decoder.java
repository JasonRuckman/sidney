package org.sidney.core.encoding.int32;

import org.sidney.core.encoding.Decoder;

public interface Int32Decoder extends Decoder {
    int nextInt();

    int[] nextInts(int num);
}
