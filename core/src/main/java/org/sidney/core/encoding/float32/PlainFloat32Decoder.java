package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.PlainInt32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class PlainFloat32Decoder extends AbstractDecoder implements Float32Decoder {
    private Int32Decoder int32Decoder = new PlainInt32Decoder();

    @Override
    public float nextFloat() {
        return Float.intBitsToFloat(int32Decoder.nextInt());
    }

    @Override
    public float[] nextFloats(int num) {
        float[] floats = new float[num];
        for(int i = 0; i < num; i++) {
            floats[i] = nextFloat();
        }
        return floats;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        int32Decoder.readFromStream(inputStream);
    }
}