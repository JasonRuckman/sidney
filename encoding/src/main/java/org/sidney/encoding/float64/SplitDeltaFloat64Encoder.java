package org.sidney.encoding.float64;

import org.sidney.encoding.bool.BoolDecoder;
import org.sidney.encoding.bool.BoolEncoder;
import org.sidney.encoding.bool.EWAHBoolEncoder;
import org.sidney.encoding.int32.DeltaInt32Encoder;
import org.sidney.encoding.int32.Int32Encoder;
import org.sidney.encoding.int64.DeltaInt64Encoder;
import org.sidney.encoding.int64.Int64Encoder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Splits a double precision float into exponent and mantissa.  Delta codes the exponent as a 16bit integer, Delta codes the
 * mantissa as a long
 */
public class SplitDeltaFloat64Encoder implements Float64Encoder  {
    private final Int32Encoder exponentEncoder = new DeltaInt32Encoder();
    private final Int64Encoder mantissaEncoder = new DeltaInt64Encoder();

    @Override
    public void writeDouble(double value) {
        long bits = Double.doubleToLongBits(value);
        int exponent = (int) (bits >>> 52);
        long mantissa = bits & ((1L << 52) - 1);

        exponentEncoder.writeInt(exponent);
        mantissaEncoder.writeLong(mantissa);
    }

    @Override
    public void writeDoubles(double[] values) {
        for(double value : values) {
            writeDouble(value);
        }
    }

    @Override
    public void reset() {
        exponentEncoder.reset();
        mantissaEncoder.reset();
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        exponentEncoder.writeToStream(outputStream);
        mantissaEncoder.writeToStream(outputStream);
    }
}