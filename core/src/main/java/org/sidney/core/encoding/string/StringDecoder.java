package org.sidney.core.encoding.string;

import org.sidney.core.encoding.Decoder;

public interface StringDecoder extends Decoder {
    String readString();
    String[] readStrings(int num);
}
