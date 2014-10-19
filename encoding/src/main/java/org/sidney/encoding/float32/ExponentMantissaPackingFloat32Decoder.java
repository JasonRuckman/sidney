package org.sidney.encoding.float32;

import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.FastBitPackInt32Decoder;
import org.sidney.encoding.int32.Int32Decoder;

import java.io.IOException;
import java.io.InputStream;

public class ExponentMantissaPackingFloat32Decoder extends AbstractDecoder implements Float32Decoder {
    private final Int32Decoder exponentDecoder = new DeltaBitPackingInt32Decoder();
    private final Int32Decoder mantissaDecoder = new FastBitPackInt32Decoder();

    @Override
    public float nextFloat() {
        int exp = exponentDecoder.nextInt();
        int mantissa = mantissaDecoder.nextInt();

        return Float.intBitsToFloat(exp << 23 | mantissa);
    }

    @Override
    public float[] nextFloats(int num) {
        float[] result = new float[num];
        for (int i = 0; i < num; i++) {
            result[i] = nextFloat();
        }
        return result;
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