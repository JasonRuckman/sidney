package org.sidney.encoding.string;

import org.sidney.encoding.Encoder;

public interface StringEncoder extends Encoder {
    void writeString(String s);
}
