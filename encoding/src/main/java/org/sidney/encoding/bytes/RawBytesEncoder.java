package org.sidney.encoding.bytes;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

public class RawBytesEncoder extends AbstractEncoder implements BytesEncoder {
    @Override
    public void writeByte(byte b) {
        writeByteInternal(b);
    }

    @Override
    public void writeBytes(byte[] bytes) {
        writeBytes(bytes, 0, bytes.length);
    }

    @Override
    public void writeBytes(byte[] bytes, int offset, int length) {
        writeBytesInternal(bytes, offset, length);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
