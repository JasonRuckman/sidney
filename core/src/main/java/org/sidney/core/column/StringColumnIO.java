package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.string.StringDecoder;
import org.sidney.core.encoding.string.StringEncoder;

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
    public Encoder getEncoder() {
        return encoder;
    }

    @Override
    public Decoder getDecoder() {
        return decoder;
    }
}
