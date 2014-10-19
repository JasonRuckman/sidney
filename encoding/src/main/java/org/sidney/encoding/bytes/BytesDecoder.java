package org.sidney.encoding.bytes;

import org.sidney.encoding.Decoder;

public interface BytesDecoder extends Decoder {
    byte readByte();
    byte[] readBytes(int num);
}
