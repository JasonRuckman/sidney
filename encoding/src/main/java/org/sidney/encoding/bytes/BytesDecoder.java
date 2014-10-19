package org.sidney.encoding.bytes;

import org.sidney.encoding.Decoder;

public interface BytesDecoder extends Decoder {
    byte[] readBytes(int num);
}
