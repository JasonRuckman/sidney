package org.sidney.encoding.int32;

import com.esotericsoftware.kryo.io.Output;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class KryoInt32Encoder implements Int32Encoder {
    private Output output;

    public KryoInt32Encoder() {
        this(4096000);
    }

    public KryoInt32Encoder(int maxBufferSizeInBytes) {
        output = new Output(256, maxBufferSizeInBytes);
    }

    @Override
    public void writeInt(int value) {
        output.writeInt(value);
    }

    @Override
    public void writeInts(int[] values) {
        for (int value : values) {
            writeInt(value);
        }
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