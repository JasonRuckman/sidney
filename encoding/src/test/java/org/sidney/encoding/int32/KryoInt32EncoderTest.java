package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class KryoInt32EncoderTest {
    @Test
    public void testReadWrite() throws IOException {
        int nums = 65536;
        KryoInt32Encoder encoder = new KryoInt32Encoder();

        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        KryoInt32Decoder decoder = new KryoInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));

        int[] other = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, other);
    }
}
