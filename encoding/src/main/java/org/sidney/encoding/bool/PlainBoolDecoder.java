package org.sidney.encoding.bool;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;

public class PlainBoolDecoder extends AbstractDecoder implements BoolDecoder {
    @Override
    public boolean nextBool() {
        return readBoolean();
    }

    @Override
    public boolean[] nextBools(int num) {
        boolean[] booleans = new boolean[num];
        for(int i = 0; i < num; i++) {
            booleans[i] = readBoolean();
        }
        return booleans;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
