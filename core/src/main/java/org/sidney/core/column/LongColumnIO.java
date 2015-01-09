package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int64.Int64Decoder;
import org.sidney.core.encoding.int64.Int64Encoder;
import org.sidney.core.serde.WriteContext;

import java.util.Arrays;
import java.util.List;

public class LongColumnIO extends ColumnIO {
    private final Int64Encoder encoder;
    private final Int64Decoder decoder;

    public LongColumnIO(Int64Encoder encoder, Int64Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeLong(long value) {
        encoder.writeLong(value);
    }

    @Override
    public long readLong() {
        return decoder.nextLong();
    }

    @Override
    public List<Encoder> getEncoders() {
        return Arrays.asList((Encoder) encoder);
    }

    @Override
    public List<Decoder> getDecoders() {
        return Arrays.asList((Decoder) decoder);
    }
}