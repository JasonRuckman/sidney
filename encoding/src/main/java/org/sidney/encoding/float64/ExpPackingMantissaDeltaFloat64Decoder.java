package org.sidney.encoding.float64;

import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int64.DeltaInt64Decoder;
import org.sidney.encoding.int64.Int64Decoder;
import parquet.bytes.LittleEndianDataInputStream;

import java.io.IOException;
import java.io.InputStream;

public class ExpPackingMantissaDeltaFloat64Decoder implements Float64Decoder {
    private double[] doubles = new double[256];
    private int index = 0;
    private final Int32Decoder exponentDecoder = new DeltaBitPackingInt32Decoder();
    private final Int64Decoder mantissaDecoder = new DeltaInt64Decoder();

    @Override
    public double nextDouble() {
        return doubles[index++];
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
        index = 0;
        int count = new LittleEndianDataInputStream(inputStream).readInt();

        exponentDecoder.readFromStream(inputStream);
        mantissaDecoder.readFromStream(inputStream);

        for (int i = 0; i < count; i++) {
            ensureCapacity(i + 1);

            long exp = exponentDecoder.nextInt();
            long mantissa = mantissaDecoder.nextLong();
            doubles[i] = Double.longBitsToDouble((exp << 52) | mantissa);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EXPMANTISSABITPACK.name();
    }

    private void ensureCapacity(int size) {
        if (size >= doubles.length) {
            double[] buffer = new double[size * 2];
            System.arraycopy(doubles, 0, buffer, 0, doubles.length);
            doubles = buffer;
        }
    }
}
