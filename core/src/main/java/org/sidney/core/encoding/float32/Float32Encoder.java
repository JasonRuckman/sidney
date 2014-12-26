package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.Encoder;

public interface Float32Encoder extends Encoder {
    void writeFloat(float value);

    void writeFloats(float[] floats);
}