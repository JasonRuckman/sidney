package org.sidney.encoding.int64;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.runner.RunnerException;
import org.sidney.core.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class DeltaInt64EncoderTest {
    @Test
    public void readWriteSmallTest() throws IOException, RunnerException {
        int nums = 5;
        DeltaInt64Encoder encoder = new DeltaInt64Encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt64Decoder decoder = new DeltaInt64Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteMediumTest() throws IOException, RunnerException {
        int nums = 129;
        DeltaInt64Encoder encoder = new DeltaInt64Encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt64Decoder decoder = new DeltaInt64Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteLargeTest() throws IOException, RunnerException {
        int nums = 1023;
        DeltaInt64Encoder encoder = new DeltaInt64Encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt64Decoder decoder = new DeltaInt64Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }


    @Test
    public void readWriteVeryLargeTest() throws IOException, RunnerException {
        int nums = 65536;
        DeltaInt64Encoder encoder = new DeltaInt64Encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt64Decoder decoder = new DeltaInt64Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }
}
