package org.sidney.encoding.float64;

import com.esotericsoftware.kryo.io.Output;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class KryoFloat64Encoder implements Float64Encoder {
    private final Output output = new Output(256, 1024000);

    @Override
    public void writeDouble(double value) {
        output.writeDouble(value);
    }

    @Override
    public void writeDoubles(double[] values) {
        output.writeDoubles(values);
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
    public Encoding supportedEncoding() {
        return Encoding.KRYO;
    }
}
