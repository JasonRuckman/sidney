package org.sidney.encoding.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.FastBitPackInt32Decoder;
import org.sidney.encoding.int32.FastBitPackInt32Encoder;
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int32.Int32Encoder;
import org.sidney.encoding.int32.KryoInt32Decoder;
import org.sidney.encoding.int32.KryoInt32Encoder;
import org.sidney.encoding.int32.PlainInt32Decoder;
import org.sidney.encoding.int32.PlainInt32Encoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class Int32EncoderBenchmarks {
    private final int[] ints;
    private int num = 65536;
    private ThreadLocal<FastBitPackInt32Encoder> bitpackingEncoders = ThreadLocal.withInitial(FastBitPackInt32Encoder::new);
    private ThreadLocal<FastBitPackInt32Decoder> bitpackingDecoders = ThreadLocal.withInitial(FastBitPackInt32Decoder::new);
    private ThreadLocal<KryoInt32Encoder> kryoEncoders = ThreadLocal.withInitial(KryoInt32Encoder::new);
    private ThreadLocal<KryoInt32Decoder> kryoDecoders = ThreadLocal.withInitial(KryoInt32Decoder::new);
    private ThreadLocal<DeltaBitPackingInt32Encoder> deltaEncoders = ThreadLocal.withInitial(
        DeltaBitPackingInt32Encoder::new);
    private ThreadLocal<DeltaBitPackingInt32Decoder> deltaDecoders = ThreadLocal.withInitial(
        DeltaBitPackingInt32Decoder::new);
    private ThreadLocal<PlainInt32Encoder> plainEncoders = ThreadLocal.withInitial(PlainInt32Encoder::new);
    private final ThreadLocal<PlainInt32Decoder> plainDecoders = ThreadLocal.withInitial(PlainInt32Decoder::new);

    public Int32EncoderBenchmarks() {
        ints = new int[num];
        Random random = new Random(11L);
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(500);
        }
    }

    @Benchmark
    @Group("intEncoders")
    public int[] fastPforInt32Encoder() throws IOException {
        return run(bitpackingEncoders.get(), bitpackingDecoders.get());
    }

    @Benchmark
    @Group("intEncoders")
    public int[] kryoInt32Encoder() throws IOException {
        return run(kryoEncoders.get(), kryoDecoders.get());
    }

    @Benchmark
    @Group("intEncoders")
    public int[] deltaInt32Encoder() throws IOException {
        return run(deltaEncoders.get(), deltaDecoders.get());
    }

    @Benchmark
    @Group("intEncoders")
    public int[] plainInt32Encoder() throws IOException {
        return run(plainEncoders.get(), plainDecoders.get());
    }

    private int[] run(Int32Encoder encoder, Int32Decoder decoder) throws IOException {
        for (int i : ints) {
            encoder.writeInt(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextInts(num);
    }
}
