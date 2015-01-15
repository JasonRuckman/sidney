package org.sidney.core.encoding.float64;

import org.sidney.core.encoding.AbstractEncoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int64.Int64Encoder;
import org.sidney.core.encoding.int64.PlainInt64Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class PlainFloat64Encoder extends AbstractEncoder implements Float64Encoder {
    private final Int64Encoder encoder = new PlainInt64Encoder();

    @Override
    public void writeDouble(double value) {
        encoder.writeLong(Double.doubleToLongBits(value));
    }

    @Override
    public void writeDoubles(double[] values) {
        for (double value : values) {
            writeDouble(value);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void reset() {
        encoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        encoder.writeToStream(outputStream);
    }
}