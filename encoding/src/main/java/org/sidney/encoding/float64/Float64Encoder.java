package org.sidney.encoding.float64;

import org.sidney.encoding.Encoder;

import java.nio.ByteBuffer;

public interface Float64Encoder extends Encoder {
    void writeDouble(double value);
    void writeDoubles(double[] values);
}