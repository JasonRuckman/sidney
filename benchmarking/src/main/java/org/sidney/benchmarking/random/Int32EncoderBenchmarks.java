package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.*;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.int32.*;

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
        decoder.populateBufferFromStream(Bytes.wrapInStream(baos.toByteArray()));
        return decoder.nextInts(num);
    }
}
