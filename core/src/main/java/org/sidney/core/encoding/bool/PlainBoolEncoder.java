package org.sidney.core.encoding.bool;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

import static org.sidney.core.encoding.io.StreamUtils.writeIntToStream;

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
