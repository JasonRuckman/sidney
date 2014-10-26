package org.sidney.core.encoding.string;

import org.sidney.core.encoding.Encoder;

public interface StringEncoder extends Encoder {
    void writeString(String s);
    void writeStrings(String[] s);
}
