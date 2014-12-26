package org.sidney.core.encoding.float64;

import org.sidney.core.encoding.Encoder;

public interface Float64Encoder extends Encoder {
    void writeDouble(double value);

    void writeDoubles(double[] values);
}