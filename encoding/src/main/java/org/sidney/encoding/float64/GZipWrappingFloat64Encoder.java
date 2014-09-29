package org.sidney.encoding.float64;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZipWrappingFloat64Encoder implements Float64Encoder {
    private Float64Encoder delegateEncoder;

    public GZipWrappingFloat64Encoder(Float64Encoder delegateEncoder) {
        this.delegateEncoder = delegateEncoder;
    }

    @Override
    public void writeDouble(double value) {
        this.delegateEncoder.writeDouble(value);
    }

    @Override
    public void writeDoubles(double[] values) {
        this.delegateEncoder.writeDoubles(values);
    }

    @Override
    public void reset() {
        this.delegateEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        this.delegateEncoder.writeToStream(gzipOutputStream);
        gzipOutputStream.close();
    }
}
