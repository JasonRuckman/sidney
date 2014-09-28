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
import org.sidney.encoding.int32.Int32Decoder;
import org.sidney.encoding.int32.Int32Encoder;

import java.io.IOException;

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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextDoubles(num);
    }
}
