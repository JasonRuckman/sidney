package org.sidney.core.encoding.bytes;

import org.sidney.core.encoding.Decoder;

public interface BytesDecoder extends Decoder {
    byte[] readBytes(int num);
}
