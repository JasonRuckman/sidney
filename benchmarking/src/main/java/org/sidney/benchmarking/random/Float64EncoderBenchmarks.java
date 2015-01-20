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
import org.sidney.core.io.float64.Float64Decoder;
import org.sidney.core.io.float64.Float64Encoder;
import org.sidney.core.io.float64.PlainFloat64Decoder;
import org.sidney.core.io.float64.PlainFloat64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
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
    public double[] plain() throws IOException {
        return run(getEncoder(PlainFloat64Encoder.class), getDecoder(PlainFloat64Decoder.class));
    }

    private double[] run(Float64Encoder encoder, Float64Decoder decoder) throws IOException {
        for (double i : doubles) {
            encoder.writeDouble(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
        return decoder.nextDoubles(num);
    }
}