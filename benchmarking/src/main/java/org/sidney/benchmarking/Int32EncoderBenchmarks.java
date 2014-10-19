package org.sidney.benchmarking;

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

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class Int32EncoderBenchmarks extends BenchmarkingBase {
    private final int[] ints;
    private int num = 65536;

    public Int32EncoderBenchmarks() {
        ints = new int[num];
        Random random = new Random(11L);
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt();
        }
    }

    @Benchmark
    @Group("intEncoders")
    public int[] fastPforInt32Encoder() throws IOException {
        return run(getEncoder(FastBitPackInt32Encoder.class), getDecoder(FastBitPackInt32Decoder.class));
    }

    @Benchmark
    @Group("intEncoders")
    public int[] kryoInt32Encoder() throws IOException {
        return run(getEncoder(KryoInt32Encoder.class), getDecoder(KryoInt32Decoder.class));
    }

    @Benchmark
    @Group("intEncoders")
    public int[] deltaInt32Encoder() throws IOException {
        return run(getEncoder(DeltaBitPackingInt32Encoder.class), getDecoder(DeltaBitPackingInt32Decoder.class));
    }

    @Benchmark
    @Group("intEncoders")
    public int[] plainInt32Encoder() throws IOException {
        return run(getEncoder(PlainInt32Encoder.class), getDecoder(PlainInt32Decoder.class));
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
