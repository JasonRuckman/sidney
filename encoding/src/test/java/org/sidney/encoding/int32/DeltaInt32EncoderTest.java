package org.sidney.encoding.int32;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.sidney.core.Bytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class DeltaInt32EncoderTest {
    @Test
    public void readWriteSmallTest() throws IOException, RunnerException {
        int nums = 5;
        DeltaInt32Encoder encoder = new DeltaInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt32Decoder decoder = new DeltaInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteMediumTest() throws IOException, RunnerException {
        int nums = 129;
        DeltaInt32Encoder encoder = new DeltaInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt32Decoder decoder = new DeltaInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }

    @Test
    public void readWriteLargeTest() throws IOException, RunnerException {
        int nums = 1023;
        DeltaInt32Encoder encoder = new DeltaInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt32Decoder decoder = new DeltaInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }



    @Test
    public void readWriteVeryLargeTest() throws IOException, RunnerException {
        int nums = 65536;
        DeltaInt32Encoder encoder = new DeltaInt32Encoder();
        Random random = new Random(11L);
        int[] arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        DeltaInt32Decoder decoder = new DeltaInt32Decoder();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        int[] ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);

        encoder.reset();
        arr = new int[nums];
        for (int i = 0; i < nums; i++) {
            arr[i] = random.nextInt(500);
            encoder.writeInt(arr[i]);
        }
        baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        ints = decoder.nextInts(nums);
        Assert.assertArrayEquals(arr, ints);
    }
}
