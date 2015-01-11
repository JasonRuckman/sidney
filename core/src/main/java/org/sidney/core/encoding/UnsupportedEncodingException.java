package org.sidney.core.encoding;

import org.sidney.core.schema.Type;

public class UnsupportedEncodingException extends RuntimeException {
    public UnsupportedEncodingException(Encoding encoding, Type type) {
        super(String.format("Encoding %s is not supported for type %s", encoding, type));
    }
}
