package org.sidney.core.encoding.bool;

import org.sidney.core.encoding.Encoder;

public interface BoolEncoder extends Encoder {
    void writeBool(boolean value);

    void writeBools(boolean[] values);
}
