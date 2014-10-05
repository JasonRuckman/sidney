package org.sidney.encoding.float64;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class SplitDeltaFloat64EncoderTest {
    private final double[] doubles;
    private final int num = 65536;

    public SplitDeltaFloat64EncoderTest() {
        doubles = new double[num];
        Random random = new Random(11L);
        for(int i = 0; i < num; i++) {
            doubles[i] = random.nextDouble();
        }
    }

    @Test
    public void readWrite() throws IOException {
        ExpPackingMantissaDeltaFloat64Encoder kryoEncoder = new ExpPackingMantissaDeltaFloat64Encoder();
        kryoEncoder.writeDoubles(doubles);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        kryoEncoder.writeToStream(baos);

        Float64Decoder decoder = new ExpPackingMantissaDeltaFloat64Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        double[] results = decoder.nextDoubles(doubles.length);

        Assert.assertArrayEquals(doubles, results, 0);
    }
}
