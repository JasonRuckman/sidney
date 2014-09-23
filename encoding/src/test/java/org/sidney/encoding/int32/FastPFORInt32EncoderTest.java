package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FastPFORInt32EncoderTest {
    @Test
    public void testReadWrite() {
        int nums = 10000;
        FastPFORInt32Encoder encoder = new FastPFORInt32Encoder();

        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }

        byte[] buf = new byte[(nums * 4) + 4];
        encoder.writeToBuffer(buf, 0);
        FastPFORInt32Decoder fastPfor = new FastPFORInt32Decoder();
        fastPfor.readFromBuffer(buf);

        int[] other = fastPfor.nextInts(nums);
        Assert.assertArrayEquals(arr, other);
    }
}