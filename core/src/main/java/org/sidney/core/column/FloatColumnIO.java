package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.float32.Float32Decoder;
import org.sidney.core.encoding.float32.Float32Encoder;

import java.util.Arrays;
import java.util.List;

public class FloatColumnIO extends ColumnIO {
    private final Float32Encoder encoder;
    private final Float32Decoder decoder;

    public FloatColumnIO(Float32Encoder encoder, Float32Decoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeFloat(float value) {
        encoder.writeFloat(value);
    }

    @Override
    public float readFloat() {
        return decoder.nextFloat();
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
