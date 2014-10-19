package org.sidney.benchmarking;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.sidney.core.Bytes;
import org.sidney.encoding.float32.ExponentMantissaPackingFloat32Decoder;
import org.sidney.encoding.float32.ExponentMantissaPackingFloat32Encoder;
import org.sidney.encoding.float32.Float32Decoder;
import org.sidney.encoding.float32.Float32Encoder;
import org.sidney.encoding.float32.KryoFloat32Decoder;
import org.sidney.encoding.float32.KryoFloat32Encoder;

import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Fork(value = 1, warmups = 1)
public class Float32EncoderBenchmarks {
    private final int num = 65536;
    private final float[] floats;
    private final ThreadLocal<Float32Encoder> expMantissaPackingFloatEncoders = ThreadLocal.withInitial(
        ExponentMantissaPackingFloat32Encoder::new);
    private final ThreadLocal<Float32Decoder> expMantissaPackingFloatDecoders = ThreadLocal.withInitial(
        ExponentMantissaPackingFloat32Decoder::new);
    private final ThreadLocal<Float32Encoder> kryoFloatEncoders = ThreadLocal.withInitial(KryoFloat32Encoder::new);
    private final ThreadLocal<Float32Decoder> kryoFloatDecoders = ThreadLocal.withInitial(KryoFloat32Decoder::new);

    public Float32EncoderBenchmarks() {
        floats = new float[num];
        Random random = new Random(11L);
        for (int i = 0; i < floats.length; i++) {
            floats[i] = random.nextFloat();
        }
    }

    @Benchmark
    @Group("float32Encoders")
    public float[] runExpMantissaPackingFloatEncoders() throws IOException {
        return run(expMantissaPackingFloatEncoders.get(), expMantissaPackingFloatDecoders.get());
    }

    @Benchmark
    @Group("float32Encoders")
    public float[] runKryoFloat32Encoder() throws IOException {
        return run(kryoFloatEncoders.get(), kryoFloatDecoders.get());
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