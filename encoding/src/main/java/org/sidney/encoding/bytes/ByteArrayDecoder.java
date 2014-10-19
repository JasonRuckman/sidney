package org.sidney.encoding.bytes;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

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
