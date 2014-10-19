package org.sidney.encoding.bytes;

import org.sidney.encoding.Encoder;

public interface BytesEncoder extends Encoder {
    void writeBytes(byte[] bytes);
    void writeBytes(byte[] bytes, int offset, int length);
}
