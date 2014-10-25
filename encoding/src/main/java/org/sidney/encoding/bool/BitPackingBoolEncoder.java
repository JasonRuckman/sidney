package org.sidney.encoding.bool;

import org.sidney.encoding.AbstractEncoder;
import org.sidney.encoding.Encoding;

import java.io.IOException;
import java.io.OutputStream;

public class BitPackingBoolEncoder extends AbstractEncoder implements BoolEncoder {
    private int currentBitIndex = 0;

    @Override
    public void writeBool(boolean value) {
        numValues++;
        ensureCapacity(1);
        if(value) {
            getBuffer()[getPosition()] |= 1 << currentBitIndex;
        }
        if(++currentBitIndex == 8) {
            incrementPosition(1);
            currentBitIndex = 0;
        }
    }

    @Override
    public void writeBools(boolean[] values) {
        //TODO: Optimize for packing 8 at a time
        for(boolean b : values) {
            writeBool(b);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.BITPACKED.name();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        incrementPosition(1);
        super.writeToStream(outputStream);
    }
}
