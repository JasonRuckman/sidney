package org.sidney.encoding.float64;

import org.sidney.encoding.Decoder;

public interface Float64Decoder extends Decoder {
    double nextDouble();
    double[] nextDoubles(int num);
}
