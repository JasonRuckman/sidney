package org.sidney.core.encoding.string;

import org.sidney.core.encoding.AbstractEncoder;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class PlainStringEncoder extends AbstractEncoder implements StringEncoder {
    private final Charset charset = Charset.forName("UTF-8");

    public void writeString(String s) {
        numValues++;
        ByteBuffer bb = charset.encode(s);
        writeIntInternal(bb.limit());
        writeBytesInternal(bb.array(), 0, bb.limit());
    }

    @Override
    public void writeStrings(String[] strings) {
        for (String s : strings) {
            writeString(s);
        }
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public String supportedEncoding() {
        return null;
    }
}
