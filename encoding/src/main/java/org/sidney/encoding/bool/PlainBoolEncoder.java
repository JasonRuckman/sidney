package org.sidney.encoding.bool;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;
import parquet.bytes.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.OutputStream;

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
        LittleEndianDataOutputStream los = new LittleEndianDataOutputStream(outputStream);
        los.writeInt(numValues);
        los.writeInt(getPosition());
        outputStream.write(getBuffer(), 0, getPosition());
        reset();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
