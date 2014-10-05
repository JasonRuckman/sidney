package org.sidney.encoding.string;

import com.esotericsoftware.kryo.io.Output;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class KryoStringEncoder implements StringEncoder {
    private final Output output = new Output();

    @Override
    public void writeString(String s) {
        output.writeString(s);
    }

    @Override
    public void writeStrings(String[] values) {
        for(String s : values) {
            writeString(s);
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
