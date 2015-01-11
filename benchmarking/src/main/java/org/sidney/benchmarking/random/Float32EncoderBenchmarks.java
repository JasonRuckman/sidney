package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.*;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.Bytes;
import org.sidney.core.encoding.float32.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
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
    public float[] plain() throws IOException {
        return run(getEncoder(PlainFloat32Encoder.class), getDecoder(PlainFloat32Decoder.class));
    }

    @Benchmark
    @Group("float32Encoders")
    public float[] rle() throws IOException {
        return run(getEncoder(RLEFloat32Encoder.class), getDecoder(RLEFloat32Decoder.class));
    }

    private float[] run(Float32Encoder encoder, Float32Decoder decoder) throws IOException {
        for (float i : floats) {
            encoder.writeFloat(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.populateBufferFromStream(Bytes.wrapInStream(baos.toByteArray()));
        return decoder.nextFloats(num);
    }
}