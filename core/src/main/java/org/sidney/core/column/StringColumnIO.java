package org.sidney.core.column;

import org.sidney.core.encoding.string.StringEncoder;

public class StringColumnIO extends ColumnIO {
    private final StringEncoder encoder;

    public StringColumnIO(StringEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeString(String value) {
        encoder.writeString(value);
    }
}
