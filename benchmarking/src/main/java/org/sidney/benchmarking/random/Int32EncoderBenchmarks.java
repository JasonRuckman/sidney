package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.encoding.Bytes;
import org.sidney.encoding.int32.BitPackingInt32Decoder;
import org.sidney.encoding.int32.BitPackingInt32Encoder;
import org.sidney.encoding.int32.DeltaBitPackingInt32Decoder;
import org.sidney.encoding.int32.DeltaBitPackingInt32Encoder;
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int32.Int32Encoder;
import org.sidney.encoding.int32.PlainInt32Decoder;
import org.sidney.encoding.int32.PlainInt32Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class Int32EncoderBenchmarks extends BenchmarkingBase {
    private final int[] ints;
    private int num = 65536;

    public Int32EncoderBenchmarks() {
        ints = new int[num];
        Random random = new Random(11L);
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(65536);
        }
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

    @Benchmark
    @Group("intEncoders")
    public int[] bitpackingInt32Encoder() throws IOException {
        return run(getEncoder(BitPackingInt32Encoder.class), getDecoder(BitPackingInt32Decoder.class));
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
