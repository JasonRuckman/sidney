package org.sidney.encoding.float32;

import com.google.common.io.LittleEndianDataInputStream;
import org.sidney.encoding.AbstractDecoder;
import org.sidney.encoding.Encoding;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.FastBitPackInt32Decoder;
import org.sidney.encoding.int32.Int32Decoder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExponentMantissaPackingFloat32Decoder extends AbstractDecoder implements Float32Decoder {
    private float[] buffer = new float[256];
    private int index = 0;
    private final Int32Decoder exponentDecoder = new DeltaBitPackingInt32Decoder();
    private final Int32Decoder mantissaDecoder = new FastBitPackInt32Decoder();

    @Override
    public float nextFloat() {
        return buffer[index++];
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
        inputStream = wrapStreamIfNecessary(inputStream);

        index = 0;

        int count = new LittleEndianDataInputStream(inputStream).readInt();

        exponentDecoder.readFromStream(inputStream);
        mantissaDecoder.readFromStream(inputStream);

        for(int i = 0; i < count; i++) {
            ensureCapacity(i + 1);

            int exp = exponentDecoder.nextInt();
            int mantissa = mantissaDecoder.nextInt();

            buffer[i] = Float.intBitsToFloat(exp << 23 | mantissa);
        }
    }

    @Override
    public String supportedEncoding() {
        return Encoding.EXPMANTISSABITPACK.name();
    }

    private void ensureCapacity(int size) {
        if (size >= buffer.length) {
            float[] newBuf = new float[size * 2];
            System.arraycopy(buffer, 0, newBuf, 0, buffer.length);
            buffer = newBuf;
            ensureCapacity(size);
        }
    }
}