package org.sidney.encoding.float64;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipWrappingFloat64Decoder implements Float64Decoder {
    private Float64Decoder delegateDecoder;

    public GZipWrappingFloat64Decoder(Float64Decoder delegateDecoder) {
        this.delegateDecoder = delegateDecoder;
    }

    @Override
    public double nextDouble() {
        return delegateDecoder.nextDouble();
    }

    @Override
    public double[] nextDoubles(int num) {
        return delegateDecoder.nextDoubles(num);
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        delegateDecoder.readFromStream(new GZIPInputStream(inputStream));
    }
}