package org.sidney.core.encoding.float32;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.encoding.int32.PlainInt32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class PlainFloat32Encoder extends AbstractEncoder implements Float32Encoder {
    private final Int32Encoder encoder = new PlainInt32Encoder();

    @Override
    public void writeFloat(float value) {
        encoder.writeInt(Float.floatToIntBits(value));
    }

    @Override
    public void writeFloats(float[] floats) {
        for(float v : floats) {
            writeFloat(v);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void reset() {
        encoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        encoder.writeToStream(outputStream);
    }
}