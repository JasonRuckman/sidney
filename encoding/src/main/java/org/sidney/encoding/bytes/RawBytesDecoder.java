package org.sidney.encoding.bytes;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

public class RawBytesDecoder extends AbstractDecoder implements BytesDecoder {
    @Override
    public byte[] readBytes(int num) {
        return super.readBytes(num);
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
