package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.string.StringDecoder;
import org.sidney.core.encoding.string.StringEncoder;

import java.util.Arrays;
import java.util.List;

public class StringColumnIO extends ColumnIO {
    private final StringEncoder encoder;
    private final StringDecoder decoder;

    public StringColumnIO(StringEncoder encoder, StringDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public void writeString(String value) {
        encoder.writeString(value);
    }

    @Override
    public String readString() {
        return decoder.readString();
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
