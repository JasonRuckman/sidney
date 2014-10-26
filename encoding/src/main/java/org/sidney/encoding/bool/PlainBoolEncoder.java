package org.sidney.encoding.bool;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;
import static org.sidney.encoding.io.StreamUtils.*;

public class PlainBoolEncoder extends AbstractEncoder implements BoolEncoder {
    @Override
    public void writeBool(boolean value) {
        writeBoolean(value);
    }

    @Override
    public void writeBools(boolean[] values) {
        for(boolean value : values) {
            writeBool(value);
        }
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        writeIntToStream(numValues, outputStream);
        writeIntToStream(getPosition(), outputStream);
        outputStream.write(getBuffer(), 0, getPosition());
        reset();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
