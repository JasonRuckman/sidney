package org.sidney.encoding.bool;

import org.sidney.encoding.Encoder;

public interface BoolEncoder extends Encoder {
    void writeBool(boolean value);

    void writeBools(boolean[] values);
}
