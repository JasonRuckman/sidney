package org.sidney.core.encoding.bool;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;

public class PlainBoolEncoder extends AbstractEncoder implements BoolEncoder {
    @Override
    public void writeBool(boolean value) {
        writeBoolean(value);
    }

    @Override
    public void writeBools(boolean[] values) {
        for (boolean value : values) {
            writeBool(value);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }
}
