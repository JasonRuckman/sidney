package org.sidney.core.encoding.bytes;

import org.sidney.core.encoding.Encoder;

public interface BytesEncoder extends Encoder {
    void writeBytes(byte[] bytes);
    void writeBytes(byte[] bytes, int offset, int length);
}
