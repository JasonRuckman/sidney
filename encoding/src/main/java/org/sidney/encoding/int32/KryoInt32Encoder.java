package org.sidney.encoding.int32;

import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.io.OutputStream;

public class KryoInt32Encoder implements Int32Encoder {
    private Output output = new Output(256, 1024000);

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
        output = new Output(256, 1024000);
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        outputStream.write(output.getBuffer(), 0, output.position());
    }
}