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
import org.sidney.encoding.float64.ExpPackingFloat64Decoder;
import org.sidney.encoding.float64.ExpPackingFloat64Encoder;
import org.sidney.encoding.float64.Float64Decoder;
import org.sidney.encoding.float64.Float64Encoder;
import org.sidney.encoding.float64.KryoFloat64Decoder;
import org.sidney.encoding.float64.KryoFloat64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class Float64EncoderBenchmarks extends BenchmarkingBase {
    private final int num = 65536;
    private final double[] doubles;

    public Float64EncoderBenchmarks() {
        doubles = new double[num];
        Random random = new Random(11L);
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = random.nextDouble();
        }
    }

    @Benchmark
    @Group("float64Encoders")
    public double[] runExpPackingFloat64Encoder() throws IOException {
        return run(getEncoder(ExpPackingFloat64Encoder.class), getDecoder(ExpPackingFloat64Decoder.class));
    }

    @Benchmark
    @Group("float64Encoders")
    public double[] runKryoFloat64Encoder() throws IOException {
        return run(getEncoder(KryoFloat64Encoder.class), getDecoder(KryoFloat64Decoder.class));
    }

    private double[] run(Float64Encoder encoder, Float64Decoder decoder) throws IOException {
        for (double i : doubles) {
            encoder.writeDouble(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextDoubles(num);
    }
}