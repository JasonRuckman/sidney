package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.AbstractDecoder;

public class RLEFloat32Decoder extends AbstractDecoder implements Float32Decoder {
    @Override
    public float nextFloat() {
        return 0;
    }

    @Override
    public float[] nextFloats(int num) {
        return new float[0];
    }

    @Override
    public String supportedEncoding() {
        return null;
    }
}
