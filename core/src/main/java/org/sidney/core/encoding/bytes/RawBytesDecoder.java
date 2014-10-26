package org.sidney.core.encoding.bytes;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class RawBytesDecoder extends AbstractDecoder implements BytesDecoder {
    @Override
    public byte[] readBytes(int num) {
        return super.readBytesInternal(num);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public byte readByte() {
        return super.readByte();
    }
}
