package org.sidney.encoding.float32;

import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.FastBitPackInt32Encoder;
import org.sidney.encoding.int32.Int32Encoder;

import java.io.IOException;
import java.io.OutputStream;

public class ExponentMantissaPackingFloat32Encoder implements Float32Encoder {
    private Int32Encoder exponentEncoder = new DeltaBitPackingInt32Encoder();
    private Int32Encoder mantissaEncoder = new FastBitPackInt32Encoder();

    @Override
    public void writeFloat(float value) {
        int bits = Float.floatToIntBits(value);
        int exp = bits >>> 23;
        int mantissa = bits & ((1 << 23) - 1);

        exponentEncoder.writeInt(exp);
        mantissaEncoder.writeInt(mantissa);
    }

    @Override
    public void writeFloats(float[] floats) {
        for (float value : floats) {
            writeFloat(value);
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
