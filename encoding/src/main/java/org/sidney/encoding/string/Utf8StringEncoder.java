package org.sidney.encoding.string;

import com.google.common.base.Charsets;
import com.google.common.io.LittleEndianDataOutputStream;
import org.sidney.encoding.AbstractEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Utf8StringEncoder extends AbstractEncoder implements StringEncoder {
    private final Charset charset = Charsets.UTF_8;

    public void writeString(String s) {
        numValues++;
        writeBytes(charset.encode(s).array());
    }

    @Override
    public void writeStrings(String[] strings) {
        for(String s : strings) {
            writeString(s);
        }
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        LittleEndianDataOutputStream lis = new LittleEndianDataOutputStream(outputStream);
        lis.writeInt(numValues);
        lis.write(getBuffer(), 0, getPosition());
    }

    @Override
    public String supportedEncoding() {
        return null;
    }
}
