package org.sidney.encoding.float64;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.BitPackingInt32Decoder;
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int64.Int64Decoder;
import org.sidney.encoding.int64.PlainInt64Decoder;

import java.io.IOException;
import java.io.InputStream;

public class ExpPackingFloat64Decoder extends AbstractDecoder implements Float64Decoder {
    private final Int32Decoder exponentDecoder = new BitPackingInt32Decoder();
    private final Int64Decoder mantissaDecoder = new PlainInt64Decoder();

    @Override
    public double nextDouble() {
        long exp = exponentDecoder.nextInt();
        long mantissa = mantissaDecoder.nextLong();
        return Double.longBitsToDouble((exp << 52) | mantissa);
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
    public void readFromStream(InputStream inputStream) throws IOException {
        inputStream = inputStreamWrapIfNecessary(inputStream);

        exponentDecoder.readFromStream(inputStream);
        mantissaDecoder.readFromStream(inputStream);
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EXPMANTISSABITPACK.name();
    }
}
