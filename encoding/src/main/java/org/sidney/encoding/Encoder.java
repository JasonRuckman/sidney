package org.sidney.encoding;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {
    void reset();

    int writeToBuffer(byte[] buffer, int offset);

    void writeToStream(OutputStream outputStream) throws IOException;
}
