package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;

public class IntColumnIO extends ColumnIO {
    private final Int32Encoder encoder;
    private final Int32Decoder decoder;

    public IntColumnIO(Int32Encoder encoder, Int32Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeInt(int value) {
        encoder.writeInt(value);
    }

    @Override
    public int readInt() {
        return decoder.nextInt();
    }

    @Override
    public Encoder getEncoder() {
        return encoder;
    }

    @Override
    public Decoder getDecoder() {
        return decoder;
    }
}