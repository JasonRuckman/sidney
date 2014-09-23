package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class KryoInt32EncoderTest {
    @Test
    public void testReadWrite() {
        int nums = 10000;
        KryoInt32Encoder encoder = new KryoInt32Encoder();

        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }

        byte[] buf = new byte[65536 + 4];
        encoder.writeToBuffer(buf, 0);
        KryoInt32Decoder decoder = new KryoInt32Decoder();
        decoder.readFromBuffer(buf);

        int[] other = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, other);
    }
}
