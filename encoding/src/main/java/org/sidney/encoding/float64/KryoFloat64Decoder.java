package org.sidney.encoding.float64;

import com.esotericsoftware.kryo.io.Input;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.InputStream;

public class KryoFloat64Decoder extends AbstractDecoder implements Float64Decoder {
    private Input input;

    @Override
    public double nextDouble() {
        return input.readDouble();
    }

    @Override
    public double[] nextDoubles(int num) {
        return input.readDoubles(num);
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
