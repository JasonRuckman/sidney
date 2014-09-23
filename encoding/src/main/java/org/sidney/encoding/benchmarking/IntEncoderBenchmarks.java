package org.sidney.encoding.benchmarking;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.encoding.int32.FastPFORInt32Decoder;
import org.sidney.encoding.int32.FastPFORInt32Encoder;
import org.sidney.encoding.int32.KryoInt32Decoder;
import org.sidney.encoding.int32.KryoInt32Encoder;

import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class IntEncoderBenchmarks {
    private final int[] ints;
    private int num = 65536;
    private ThreadLocal<byte[]> buffers = ThreadLocal.withInitial(() -> new byte[num * 8 + 8]);
    private ThreadLocal<FastPFORInt32Encoder> encoders = ThreadLocal.withInitial(FastPFORInt32Encoder::new);
    private ThreadLocal<FastPFORInt32Decoder> decoders = ThreadLocal.withInitial(FastPFORInt32Decoder::new);
    private ThreadLocal<KryoInt32Encoder> kryoEncoders = ThreadLocal.withInitial(KryoInt32Encoder::new);
    private ThreadLocal<KryoInt32Decoder> kryoDecoders = ThreadLocal.withInitial(KryoInt32Decoder::new);

    public IntEncoderBenchmarks() {
        ints = new int[num];
        Random random = new Random(11L);
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(1000000);
        }
    }

    @Benchmark
    @Group("intEncoders")
    public int[] fastPforInt32Encoder() {
        FastPFORInt32Encoder encoder = encoders.get();
        encoder.reset();
        byte[] buffer = buffers.get();
        encoder.writeInts(ints);
        encoder.writeToBuffer(buffer, 0);
        FastPFORInt32Decoder decoder = decoders.get();
        decoder.readFromBuffer(buffer);
        return decoder.nextInts(num);
    }

    @Benchmark
    @Group("intEncoders")
    public int[] kryoInt32Encoder() {
        KryoInt32Encoder encoder = kryoEncoders.get();
        encoder.reset();
        byte[] buffer = buffers.get();
        encoder.writeInts(ints);
        encoder.writeToBuffer(buffer, 0);
        KryoInt32Decoder decoder = kryoDecoders.get();
        decoder.readFromBuffer(buffer);
        return decoder.nextInts(num);
    }
}
