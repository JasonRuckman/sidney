package org.sidney.core.encoding.bytes;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

public class RawBytesEncoder extends AbstractEncoder implements BytesEncoder {
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
