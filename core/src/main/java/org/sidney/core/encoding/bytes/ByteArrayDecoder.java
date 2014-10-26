package org.sidney.core.encoding.bytes;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class ByteArrayDecoder extends AbstractDecoder implements BytesDecoder {
    @Override
    public byte[] readBytes(int num) {
        int length = readIntLE();
        return readBytesInternal(length);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
