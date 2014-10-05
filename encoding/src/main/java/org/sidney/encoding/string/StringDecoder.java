package org.sidney.encoding.string;

import org.sidney.encoding.Decoder;

public interface StringDecoder extends Decoder {
    String readString();
    String[] readStrings(int num);
}
