package org.sidney.encoding.benchmarking;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.io.FastByteArrayOutputStream;
import org.sidney.encoding.float64.Float64Decoder;
import org.sidney.encoding.float64.Float64Encoder;
import org.sidney.encoding.float64.KryoFloat64Decoder;
import org.sidney.encoding.float64.KryoFloat64Encoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class FloatEncoderBenchmarks {
    private final double[] doubles;
    private final int num = 65536;
    private final ThreadLocal<Float64Encoder> kryoEncoders = ThreadLocal.withInitial(KryoFloat64Encoder::new);
    private final ThreadLocal<Float64Decoder> kryoDecoders = ThreadLocal.withInitial(KryoFloat64Decoder::new);

    public FloatEncoderBenchmarks() {
        doubles = new double[num];
        Random random = new Random(11L);
        double[] candidates = new double[]{0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0};
        for (int i = 0; i < num; i++) {
            doubles[i] = candidates[random.nextInt(candidates.length - 1)];
        }
    }

    @Benchmark
    @Group("doubleEncoders")
    public double[] kryoFloat64Encoder() throws IOException {
        return run(kryoEncoders.get(), kryoDecoders.get());
    }

    private double[] run(Float64Encoder encoder, Float64Decoder decoder) throws IOException {
        for (double i : doubles) {
            encoder.writeDouble(i);
        }
        FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(baos.getInputStream());
        return decoder.nextDoubles(num);
    }
}
