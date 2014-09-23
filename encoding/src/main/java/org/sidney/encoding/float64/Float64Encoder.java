package org.sidney.encoding.float64;

import org.sidney.encoding.Encoder;

import java.nio.ByteBuffer;

public interface Float64Encoder extends Encoder {
    void writeFloat64(double value, ByteBuffer buffer, int offset);

    void writeFloat64s(double[] values, ByteBuffer buffer, int offset);
}