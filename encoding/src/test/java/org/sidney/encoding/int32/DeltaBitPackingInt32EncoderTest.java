package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.runner.RunnerException;
import org.sidney.core.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class DeltaBitPackingInt32EncoderTest {
    @Test
    public void readWriteSmallTest() throws IOException, RunnerException {
        int nums = 5;
        DeltaBitPackingInt32Encoder encoder = new DeltaBitPackingInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = Float.floatToIntBits(random.nextFloat());
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaBitPackingInt32Decoder decoder = new DeltaBitPackingInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteMediumTest() throws IOException, RunnerException {
        int nums = 129;
        DeltaBitPackingInt32Encoder encoder = new DeltaBitPackingInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaBitPackingInt32Decoder decoder = new DeltaBitPackingInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteLargeTest() throws IOException, RunnerException {
        int nums = 1023;
        DeltaBitPackingInt32Encoder encoder = new DeltaBitPackingInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt();
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaBitPackingInt32Decoder decoder = new DeltaBitPackingInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }



    @Test
    public void readWriteVeryLargeTest() throws IOException, RunnerException {
        int nums = 65536;
        DeltaBitPackingInt32Encoder encoder = new DeltaBitPackingInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = Float.floatToIntBits(random.nextFloat());
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaBitPackingInt32Decoder decoder = new DeltaBitPackingInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }
}
