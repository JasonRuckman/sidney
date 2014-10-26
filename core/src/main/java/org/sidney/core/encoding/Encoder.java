package org.sidney.core.encoding;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {
    void reset();
    void writeToStream(OutputStream outputStream) throws IOException;
    String supportedEncoding();
}
