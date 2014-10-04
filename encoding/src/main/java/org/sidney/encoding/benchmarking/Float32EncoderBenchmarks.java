package org.sidney.encoding.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.float32.Float32Decoder;
import org.sidney.encoding.float32.Float32Encoder;
import org.sidney.encoding.float32.SplitDeltaFloat32Decoder;
import org.sidney.encoding.float32.SplitDeltaFloat32Encoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Group)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class Float32EncoderBenchmarks {
    private final int num = 65536;
    private final float[] floats;
    private final ThreadLocal<Float32Encoder> deltaFloatEncoders = ThreadLocal.withInitial(SplitDeltaFloat32Encoder::new);
    private final ThreadLocal<Float32Decoder> deltaFloatDecoders = ThreadLocal.withInitial(SplitDeltaFloat32Decoder::new);

    public Float32EncoderBenchmarks() {
        floats = new float[num];
        Random random = new Random(11L);
        for (int i = 0; i < floats.length; i++) {
            floats[i] = random.nextFloat();
        }
    }

    @Benchmark
    @Group("Float32Encoders")
    public float[] runSplitDeltaFloatEncoder() throws IOException {
        return run(deltaFloatEncoders.get(), deltaFloatDecoders.get());
    }

    @Benchmark
    @Group("Float32Encoders")
    public float[] runKryoFloatEncoder() throws IOException {
        return run(deltaFloatEncoders.get(), deltaFloatDecoders.get());
    }

    private float[] run(Float32Encoder encoder, Float32Decoder decoder) throws IOException {
        for (float i : floats) {
            encoder.writeFloat(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(Bytes.wrap(baos.toByteArray()));
        return decoder.nextFloats(num);
    }
}