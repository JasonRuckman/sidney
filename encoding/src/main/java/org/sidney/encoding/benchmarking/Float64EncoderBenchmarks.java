package org.sidney.encoding.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.float64.Float64Decoder;
import org.sidney.encoding.float64.Float64Encoder;
import org.sidney.encoding.float64.KryoFloat64Decoder;
import org.sidney.encoding.float64.KryoFloat64Encoder;
import org.sidney.encoding.float64.SplitDeltaFloat64Decoder;
import org.sidney.encoding.float64.SplitDeltaFloat64Encoder;
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
public class Float64EncoderBenchmarks {
    private final int num = 65536;
    private final double[] doubles;
    private final ThreadLocal<Float64Encoder> deltaFloatEncoders = ThreadLocal.withInitial(KryoFloat64Encoder::new);
    private final ThreadLocal<Float64Decoder> deltaFloatDecoders = ThreadLocal.withInitial(KryoFloat64Decoder::new);
    private final ThreadLocal<Float64Encoder> kryoFloatEncoders = ThreadLocal.withInitial(SplitDeltaFloat64Encoder::new);
    private final ThreadLocal<Float64Decoder> kryoFloatDecoders = ThreadLocal.withInitial(SplitDeltaFloat64Decoder::new);

    public Float64EncoderBenchmarks() {
        doubles = new double[num];
        Random random = new Random(11L);
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = random.nextDouble();
        }
    }

    @Benchmark
    @Group("float64Encoders")
    public double[] runSplitDeltaFloatEncoder() throws IOException {
        return run(deltaFloatEncoders.get(), deltaFloatDecoders.get());
    }

    @Benchmark
    @Group("float64Encoders")
    public double[] runKryoFloatEncoder() throws IOException {
        return run(kryoFloatEncoders.get(), kryoFloatDecoders.get());
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