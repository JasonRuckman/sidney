package org.sidney.encoding.float64;

import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.Int32Encoder;
import org.sidney.encoding.int64.Int64Encoder;
import org.sidney.encoding.int64.PlainInt64Encoder;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Splits a double precision float into exponent and mantissa.  Delta codes the exponent as a 32bit integer, Delta codes the
 * mantissa as a long
 */
public class ExpPackingMantissaDeltaFloat64Encoder implements Float64Encoder  {
    private final Int32Encoder exponentEncoder = new DeltaBitPackingInt32Encoder();
    private final Int64Encoder mantissaEncoder = new PlainInt64Encoder();

    @Override
    public void writeDouble(double value) {
        long bits = Double.doubleToLongBits(value);
        long exp = bits >>> 52;
        long mantissa = bits & ((1L << 52) - 1);

        exponentEncoder.writeInt((int) exp);
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

    @Override
    public String supportedEncoding() {
        return Encoding.EXPMANTISSABITPACK.name();
    }
}