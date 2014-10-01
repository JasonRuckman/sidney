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
        Int64Encoder encoder = encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        Int64Decoder decoder = decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteMediumTest() throws IOException, RunnerException {
        int nums = 129;
        Int64Encoder encoder = encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        Int64Decoder decoder = decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteLargeTest() throws IOException, RunnerException {
        int nums = 1023;
        Int64Encoder encoder = encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        Int64Decoder decoder = decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }


    @Test
    public void readWriteVeryLargeTest() throws IOException, RunnerException {
        int nums = 65536;
        Int64Encoder encoder = encoder();
        Random random = new Random(11L);
        long[] arr = new long[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeLong(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        Int64Decoder decoder = decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        long[] ints = decoder.nextLongs(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    private Int64Encoder encoder() {
        return new DeltaInt64Encoder();
    }

    private Int64Decoder decoder() {
        return new DeltaInt64Decoder();
    }
}
