package org.sidney.core.encoding.float64;

import org.sidney.core.encoding.AbstractDecoder;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.encoding.int64.Int64Decoder;
import org.sidney.core.encoding.int64.PlainInt64Decoder;

import java.io.IOException;
import java.io.InputStream;

public class PlainFloat64Decoder extends AbstractDecoder implements Float64Decoder {
    private final Int64Decoder decoder = new PlainInt64Decoder();

    @Override
    public double nextDouble() {
        return Double.longBitsToDouble(decoder.nextLong());
    }

    @Override
    public double[] nextDoubles(int num) {
        double[] results = new double[num];
        for (int i = 0; i < num; i++) {
            results[i] = nextDouble();
        }
        return results;
    }

    @Override
    public String supportedEncoding() {
        return Encoding.PLAIN.name();
    }

    @Override
    public void readFromStream(InputStream inputStream) throws IOException {
        decoder.readFromStream(inputStream);
    }
}
