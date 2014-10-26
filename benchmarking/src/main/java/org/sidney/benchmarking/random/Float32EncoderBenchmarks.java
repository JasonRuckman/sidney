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
import org.sidney.encoding.float32.ExponentMantissaPackingFloat32Decoder;
import org.sidney.encoding.float32.ExponentMantissaPackingFloat32Encoder;
import org.sidney.encoding.float32.Float32Decoder;
import org.sidney.encoding.float32.Float32Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class Float32EncoderBenchmarks extends BenchmarkingBase {
    private final int num = 65536;
    private final float[] floats;

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
        return run(getEncoder(ExponentMantissaPackingFloat32Encoder.class), getDecoder(ExponentMantissaPackingFloat32Decoder.class));
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