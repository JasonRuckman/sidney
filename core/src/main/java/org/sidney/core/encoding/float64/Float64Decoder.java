package org.sidney.core.encoding.float64;

import org.sidney.core.encoding.Decoder;

public interface Float64Decoder extends Decoder {
    double nextDouble();
    double[] nextDoubles(int num);
}
