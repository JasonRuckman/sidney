package org.sidney.encoding.float32;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class SplitDeltaFloat32EncoderTest {
    private final float[] floats;
    private final int num = 65536;

    public SplitDeltaFloat32EncoderTest() {
        floats = new float[num];
        Random random = new Random(11L);
        for(int i = 0; i < num; i++) {
            floats[i] = random.nextFloat();
        }
    }

    @Test
    public void readWrite() throws IOException {
        ExponentMantissaPackingFloat32Decoder kryoEncoder = new ExponentMantissaPackingFloat32Decoder();
        kryoEncoder.writeFloats(floats);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        kryoEncoder.writeToStream(baos);

        Float32Decoder decoder = new ExponentMantissaPackingFloat32Encoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        float[] results = decoder.nextFloats(floats.length);

        Assert.assertArrayEquals(floats, results, 0);
    }
}
