package org.sidney.encoding.float32;

import org.sidney.encoding.Decoder;

public interface Float32Decoder extends Decoder {
    float nextFloat();
    float[] nextFloats(int num);
}
