package org.sidney.core.encoding.bool;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;

public class BitPackingBoolDecoder extends AbstractDecoder implements BoolDecoder {
    private int currentBitIndex = 0;

    @Override
    public boolean nextBool() {
        boolean result = false;

        if ((getBuffer()[getPosition()] & 1 << currentBitIndex) > 0) {
            result = true;
        }

        if (++currentBitIndex == 8) {
            incrementPosition(1);
            currentBitIndex = 0;
        }

        return result;
    }

    @Override
    public boolean[] nextBools(int num) {
        boolean[] booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = nextBool();
        }
        return booleans;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }
}
