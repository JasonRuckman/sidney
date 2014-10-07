package org.sidney.encoding.float32;

import com.esotericsoftware.kryo.io.Output;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class KryoFloat32Encoder implements Float32Encoder {
    private final Output output;

    public KryoFloat32Encoder() {
        this(4096000);
    }

    public KryoFloat32Encoder(int maxBufferSizeInBytes) {
        output = new Output(256, maxBufferSizeInBytes);
    }

    @Override
    public void writeFloat(float value) {
        output.writeFloat(value);
    }

    @Override
    public void writeFloats(float[] floats) {
        output.writeFloats(floats);
    }

    @Override
    public void reset() {
        output.setPosition(0);
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(output.getBuffer(), 0, output.position());
    }

    @Override
    public String supportedEncoding() {
        return Encoding.KRYO.name();
    }
}