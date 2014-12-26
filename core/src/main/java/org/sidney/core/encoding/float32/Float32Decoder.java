package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.Decoder;

public interface Float32Decoder extends Decoder {
    float nextFloat();

    float[] nextFloats(int num);
}
