package org.sidney.core.encoding;

public class UnsupportedEncodingException extends RuntimeException {
    public UnsupportedEncodingException(Encoding encoding, Class requestedType) {
        super(String.format("Encoding %s is not supported for type %s", encoding, requestedType));
    }
}
