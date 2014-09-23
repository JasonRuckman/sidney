package org.sidney.encoding.bool;

import org.sidney.encoding.Decoder;

public interface BoolDecoder extends Decoder {
    boolean nextBool();

    boolean[] nextBools(int num);
}
