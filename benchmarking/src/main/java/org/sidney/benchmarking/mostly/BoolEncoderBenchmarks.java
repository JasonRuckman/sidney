package org.sidney.benchmarking.mostly;

import org.openjdk.jmh.annotations.*;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.bool.*;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
@Fork(1)
public class BoolEncoderBenchmarks extends BenchmarkingBase {
    private final boolean[] booleans;
    private final Random random = new Random(11L);
    private int num = 65536;

    public BoolEncoderBenchmarks() {
        booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = random.nextInt() % 50 == 0;
        }
    }

    @Benchmark
    @Group("boolEncodingMostly")
    public boolean[] roaringBoolEncoderUncompressed() throws IOException {
        return run(getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingMostly")
    public boolean[] plainEncoderUncompressed() throws IOException {
        return run(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingMostly")
    public boolean[] ewahBoolEncoderUncompressed() throws IOException {
        return run(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingMostly")
    public boolean[] bitPackingEncoderUncompressed() throws IOException {
        return run(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPMostly")
    public boolean[] roaringBoolEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPMostly")
    public boolean[] plainEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPMostly")
    public boolean[] ewahBoolEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPMostly")
    public boolean[] bitPackingEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyMostly")
    public boolean[] roaringBoolEncoderSnappy() throws IOException {
        return runSnappyCompressed(
                getEncoder(RoaringBitmapBoolEncoder.class), getDecoder(RoaringBitmapBoolDecoder.class)
        );
    }

    @Benchmark
    @Group("boolEncodingSnappyMostly")
    public boolean[] plainEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyMostly")
    public boolean[] ewahBoolEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingSnappyMostly")
    public boolean[] bitPackingEncoderSnappy() throws IOException {
        return runSnappyCompressed(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    private boolean[] run(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        decoder.populateBufferFromStream(Bytes.wrapInStream(baos.toByteArray()));
        return decoder.nextBools(num);
    }

    private boolean[] runSnappyCompressed(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SnappyOutputStream sos = new SnappyOutputStream(baos);
        encoder.writeToStream(sos);
        sos.close();
        decoder.populateBufferFromStream(new SnappyInputStream(Bytes.wrapInStream(baos.toByteArray())));
        return decoder.nextBools(num);
    }

    private boolean[] runGZipCompressed(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        encoder.writeToStream(gos);
        gos.close();
        decoder.populateBufferFromStream(
                new BufferedInputStream(new GZIPInputStream(
                        Bytes.wrapInStream(baos.toByteArray()))
                )
        );
        return decoder.nextBools(num);
    }
}