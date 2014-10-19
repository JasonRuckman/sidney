package org.sidney.encoding.bytes;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

public class ByteArrayEncoder extends AbstractEncoder implements BytesEncoder {
    @Override
    public void writeBytes(byte[] bytes) {
        writeBytes(bytes, 0, bytes.length);
    }

    @Override
    public void writeBytes(byte[] bytes, int offset, int length) {
        writeIntLE(length);
        writeBytesInternal(bytes, offset, length);
        numValues++;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
