package org.sidney.encoding.float32;

import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.Int32Encoder;
import parquet.bytes.LittleEndianDataOutputStream;

import java.io.IOException;
import java.io.OutputStream;

public class SplitDeltaFloat32Encoder implements Float32Encoder {
    private Int32Encoder exponentEncoder = new DeltaBitPackingInt32Encoder();
    private Int32Encoder mantissaEncoder = new DeltaBitPackingInt32Encoder();
    private int count = 0;

    @Override
    public void writeFloat(float value) {
        int bits = Float.floatToIntBits(value);
        int exp = bits >>> 23;
        int mantissa = bits & ((1 << 23) - 1);

        exponentEncoder.writeInt(exp);
        mantissaEncoder.writeInt(mantissa);
        count++;
    }

    @Override
    public void writeFloats(float[] floats) {
        for(float value : floats) {
            writeFloat(value);
        }
    }

    @Override
    public void reset() {
        exponentEncoder.reset();
        mantissaEncoder.reset();
        count = 0;
    }

    @Override
    public void writeToStream(OutputStream outputStream) throws IOException {
        new LittleEndianDataOutputStream(outputStream).writeInt(count);
        exponentEncoder.writeToStream(outputStream);
        mantissaEncoder.writeToStream(outputStream);
    }
}
