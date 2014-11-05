package org.sidney.core.encoding;

import java.io.IOException;
import java.io.InputStream;

public interface Decoder {
    void populateBufferFromStream(InputStream inputStream) throws IOException;
    String supportedEncoding();
}
