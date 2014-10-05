package org.sidney.encoding.int64;

import com.esotericsoftware.kryo.io.Output;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class KryoInt64Encoder implements Int64Encoder {
    private final Output output = new Output(256, 1024000);

    @Override
    public void writeLong(long value) {
        output.writeLong(value);
    }

    @Override
    public void writeLongs(long[] values) {
        output.writeLongs(values);
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
