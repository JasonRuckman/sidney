package org.sidney.encoding.int32;

import java.io.IOException;
import java.io.OutputStream;

public class EnumCompositeEncoder implements EnumEncoder {
    private Int32Encoder delegateEncoder;

    public EnumCompositeEncoder(Int32Encoder delegateEncoder) {
        this.delegateEncoder = delegateEncoder;
    }

    @Override
    public <T extends Enum> void writeEnum(T value) {
        delegateEncoder.writeInt(value.ordinal());
    }

    @Override
    public <T extends Enum> void writeEnums(T[] value) {
        for (T t : value) {
            writeEnum(t);
        }
    }

    @Override
    public void reset() {
        delegateEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        delegateEncoder.writeToStream(outputStream);
    }
}