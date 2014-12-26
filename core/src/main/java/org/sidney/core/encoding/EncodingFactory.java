package org.sidney.core.encoding;

import org.sidney.core.encoding.int32.BitPackingInt32Encoder;
import org.sidney.core.encoding.int32.Int32Encoder;

public class EncodingFactory {
    public static Int32Encoder newDefinitionRepetitionEncoder() {
        return new BitPackingInt32Encoder();
    }
}
