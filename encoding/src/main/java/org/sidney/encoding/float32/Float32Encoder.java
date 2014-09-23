package org.sidney.encoding.float32;

import org.sidney.encoding.Encoder;

import java.nio.ByteBuffer;

public interface Float32Encoder extends Encoder {
    void writeFloat(float value, ByteBuffer buffer, int offset);

    void writeFloats(float[] values, ByteBuffer buffer, int offset);
}