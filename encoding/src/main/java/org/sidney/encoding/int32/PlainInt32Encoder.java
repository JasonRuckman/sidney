package org.sidney.encoding.int32;

import com.google.common.io.LittleEndianDataOutputStream;
import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class PlainInt32Encoder extends AbstractEncoder implements Int32Encoder {
    @Override
    public void writeInt(int value) {
        writeIntLE(value);
        numValues++;
    }

    @Override
    public void writeInts(int[] values) {
        for(int value : values) {
            writeInt(value);
        }
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream dos = new LittleEndianDataOutputStream(outputStream);

        dos.writeInt(numValues);
        dos.writeInt(getPosition());
        dos.write(getBuffer(), 0, getPosition());

        dos.flush();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
