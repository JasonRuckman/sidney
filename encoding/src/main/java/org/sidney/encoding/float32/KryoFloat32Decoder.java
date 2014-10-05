package org.sidney.encoding.float32;

import com.esotericsoftware.kryo.io.Input;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class KryoFloat32Decoder implements Float32Decoder {
    private Input input;

    @Override
    public float nextFloat() {
        return input.readFloat();
    }

    @Override
    public float[] nextFloats(int num) {
        return input.readFloats(num);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        input = new Input(inputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.KRYO.name();
    }
}
