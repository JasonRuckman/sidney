package org.sidney.core.encoding;

import java.io.IOException;
import java.io.InputStream;

public interface Decoder {
    void readFromStream(InputStream inputStream) throws IOException;
    String supportedEncoding();
}
