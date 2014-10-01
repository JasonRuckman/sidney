package org.sidney.encoding.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.float64.KryoFloat64Encoder;
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int32.Int32Encoder;
import org.sidney.encoding.int64.BitPackingInt64Decoder;
import org.sidney.encoding.int64.BitPackingInt64Encoder;
import org.sidney.encoding.int64.DeltaInt64Decoder;
import org.sidney.encoding.int64.DeltaInt64Encoder;
import org.sidney.encoding.int64.Int64Decoder;
import org.sidney.encoding.int64.Int64Encoder;
import org.sidney.encoding.int64.KryoInt64Decoder;
import org.sidney.encoding.int64.KryoInt64Encoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class Int64EncoderBenchmarks {
    private final int num = 65536;
    private final long[] longs;
    private final ThreadLocal<Int64Encoder> deltaInt64Encoders = ThreadLocal.withInitial(DeltaInt64Encoder::new);
    private final ThreadLocal<Int64Decoder> deltaInt64Decoders = ThreadLocal.withInitial(DeltaInt64Decoder::new);
    private final ThreadLocal<Int64Encoder> kryoInt64Encoders = ThreadLocal.withInitial(KryoInt64Encoder::new);
    private final ThreadLocal<Int64Decoder> kryoInt64Decoders = ThreadLocal.withInitial(KryoInt64Decoder::new);
    private final ThreadLocal<Int64Encoder> bitpackingInt64Encoders = ThreadLocal.withInitial(BitPackingInt64Encoder::new);
    private final ThreadLocal<Int64Decoder> bitpackingInt64Decoders = ThreadLocal.withInitial(BitPackingInt64Decoder::new);

    public Int64EncoderBenchmarks() {
        longs = new long[num];
        Random random = new Random(11L);
        for (int i = 0; i < longs.length; i++) {
            longs[i] = random.nextLong();
        }
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] deltaInt64Encoder() throws IOException {
        return run(deltaInt64Encoders.get(), deltaInt64Decoders.get());
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] bitpacking64Encoder() throws IOException {
        return run(bitpackingInt64Encoders.get(), bitpackingInt64Decoders.get());
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] kryoInt64Encoder() throws IOException {
        return run(kryoInt64Encoders.get(), kryoInt64Decoders.get());
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