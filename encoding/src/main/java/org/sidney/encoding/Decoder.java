package org.sidney.encoding;

import java.io.IOException;
import java.io.InputStream;

public interface Decoder {
    void readFromStream(InputStream inputStream) throws IOException;
    Encoding supportedEncoding();
}
