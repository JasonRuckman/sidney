package org.sidney.encoding.float32;

import org.sidney.encoding.Encoder;

public interface Float32Encoder extends Encoder {
    void writeFloat(float value);
    void writeFloats(float[] floats);
}