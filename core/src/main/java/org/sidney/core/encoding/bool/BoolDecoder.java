package org.sidney.core.encoding.bool;

import org.sidney.core.encoding.Decoder;

public interface BoolDecoder extends Decoder {
    boolean nextBool();

    boolean[] nextBools(int num);
}
