/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.benchmarking.random;

import org.openjdk.jmh.annotations.*;
import org.sidney.benchmarking.BenchmarkingBase;
import org.sidney.core.io.float32.Float32Decoder;
import org.sidney.core.io.float32.Float32Encoder;
import org.sidney.core.io.float32.Plain;
import org.sidney.core.io.float32.RLE;

import java.io.ByteArrayInputStream;
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
        return run(getEncoder(Plain.PlainFloat32Encoder.class), getDecoder(Plain.PlainFloat32Decoder.class));
    }

    @Benchmark
    @Group("float32Encoders")
    public float[] rle() throws IOException {
        return run(getEncoder(RLE.RLEFloat32Encoder.class), getDecoder(RLE.RLEFloat32Decoder.class));
    }

    private float[] run(Float32Encoder encoder, Float32Decoder decoder) throws IOException {
        for (float i : floats) {
            encoder.writeFloat(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
        return decoder.nextFloats(num);
    }
}