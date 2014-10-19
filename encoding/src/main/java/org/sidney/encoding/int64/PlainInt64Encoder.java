package org.sidney.encoding.int64;

import com.google.common.io.LittleEndianDataOutputStream;
import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class PlainInt64Encoder extends AbstractEncoder implements Int64Encoder {
    @Override
    public void writeLong(long value) {
        writeLongLE(value);
        numValues++;
    }

    @Override
    public void writeLongs(long[] values) {
        for(long l : values) {
            writeLong(l);
        }
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream los = new LittleEndianDataOutputStream(outputStream);

        los.writeInt(numValues);
        los.writeInt(getPosition());
        los.write(getBuffer(), 0, getPosition());

        los.flush();
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}