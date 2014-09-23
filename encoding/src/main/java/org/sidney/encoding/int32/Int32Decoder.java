package org.sidney.encoding.int32;

import org.sidney.encoding.Decoder;

public interface Int32Decoder extends Decoder {
    int nextInt();

    int[] nextInts(int num);
}
