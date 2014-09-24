package org.sidney.encoding.int32;

import org.junit.Test;

import java.util.Random;

public class DeltaInt32EncoderTest {
    @Test
    public void readWriteTest() {
        int nums = 10000;
        DeltaInt32Encoder encoder = new DeltaInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        byte[] buffer = new byte[65536];
        encoder.writeToBuffer(buffer, 0);
    }
}
