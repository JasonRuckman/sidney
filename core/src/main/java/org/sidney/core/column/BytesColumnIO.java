package org.sidney.core.column;

import org.sidney.core.encoding.bytes.BytesEncoder;

public class BytesColumnIO extends ColumnIO {
    private final BytesEncoder encoder;

    public BytesColumnIO(BytesEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void writeBytes(byte[] bytes) {
        this.encoder.writeBytes(bytes);
    }
}
