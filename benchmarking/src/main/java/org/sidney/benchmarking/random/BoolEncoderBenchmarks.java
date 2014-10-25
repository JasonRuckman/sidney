package org.sidney.benchmarking.random;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.encoding.Bytes;
import org.sidney.encoding.bool.BitPackingBoolDecoder;
import org.sidney.encoding.bool.BitPackingBoolEncoder;
import org.sidney.encoding.bool.BoolDecoder;
import org.sidney.encoding.bool.BoolEncoder;
import org.sidney.encoding.bool.EWAHBitmapBoolDecoder;
import org.sidney.encoding.bool.EWAHBitmapBoolEncoder;
import org.sidney.encoding.bool.PlainBoolDecoder;
import org.sidney.encoding.bool.PlainBoolEncoder;
import org.sidney.encoding.bool.RoaringBitmapBoolDecoder;
import org.sidney.encoding.bool.RoaringBitmapBoolEncoder;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class BoolEncoderBenchmarks extends BenchmarkingBase {
    private final boolean[] booleans;
    private final Random random = new Random(11L);
    private int num = 65536;

    public BoolEncoderBenchmarks() {
        booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = random.nextBoolean();
        }
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] roaringBoolEncoderUncompressed() throws IOException {
        return run(getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] plainEncoderUncompressed() throws IOException {
        return run(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] ewahBoolEncoderUncompressed() throws IOException {
        return run(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] bitPackingEncoderUncompressed() throws IOException {
        return run(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] roaringBoolEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] plainEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] ewahBoolEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] bitPackingEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyRandom")
    public boolean[] roaringBoolEncoderSnappy() throws IOException {
        return runSnappyCompressed(
            getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class)
        );
    }

    @Benchmark
    @Group("boolEncodingSnappyRandom")
    public boolean[] plainEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyRandom")
    public boolean[] ewahBoolEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyRandom")
    public boolean[] bitPackingEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    private boolean[] run(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextBools(num);
    }

    private boolean[] runSnappyCompressed(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SnappyOutputStream sos = new SnappyOutputStream(baos);
        encoder.writeToStream(sos);
        sos.close();
        decoder.readFromStream(new SnappyInputStream(Bytes.wrap(baos.toByteArray())));
        return decoder.nextBools(num);
    }

    private boolean[] runGZipCompressed(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        encoder.writeToStream(gos);
        gos.close();
        decoder.readFromStream(new GZIPInputStream(Bytes.wrap(baos.toByteArray())));
        return decoder.nextBools(num);
    }
}