package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.int64.GroupVarInt64Decoder;
import org.sidney.core.encoding.int64.GroupVarInt64Encoder;
import org.sidney.core.encoding.int64.Int64Decoder;
import org.sidney.core.encoding.int64.Int64Encoder;
import org.sidney.core.encoding.int64.PlainInt64Decoder;
import org.sidney.core.encoding.int64.PlainInt64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class Int64EncoderBenchmarks extends BenchmarkingBase {
    private final int num = 65536;
    private final long[] longs;

    public Int64EncoderBenchmarks() {
        longs = new long[num];
        Random random = new Random(11L);
        for (int i = 0; i < longs.length; i++) {
            longs[i] = random.nextInt(500);
        }
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] runPlainInt64Encoder() throws IOException {
        return run(getEncoder(PlainInt64Encoder.class), getDecoder(PlainInt64Decoder.class));
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] runVarInt64Encoder() throws IOException {
        return run(getEncoder(GroupVarInt64Encoder.class), getDecoder(GroupVarInt64Decoder.class));
    }

    private long[] run(Int64Encoder encoder, Int64Decoder decoder) throws IOException {
        for (long i : longs) {
            encoder.writeLong(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextLongs(num);
    }
}