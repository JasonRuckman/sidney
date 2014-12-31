package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.float64.Float64Decoder;
import org.sidney.core.encoding.float64.Float64Encoder;

import java.util.Arrays;
import java.util.List;

public class DoubleColumnIO extends ColumnIO {
    private final Float64Encoder encoder;
    private final Float64Decoder decoder;

    public DoubleColumnIO(Float64Encoder encoder, Float64Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeDouble(double value) {
        encoder.writeDouble(value);
    }

    @Override
    public double readDouble() {
        return decoder.nextDouble();
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
