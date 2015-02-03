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
package com.github.jasonruckman.sidney.benchmarking.random;

import com.github.jasonruckman.sidney.benchmarking.BenchmarkingBase;
import com.github.jasonruckman.sidney.core.io.bool.*;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
@Fork(1)
public class BoolEncoderBenchmarks extends BenchmarkingBase {
    private final boolean[] booleans;
    private final Random random = new Random(11L);
    private int num = 65536;

    public BoolEncoderBenchmarks() {
        booleans = new boolean[num];
        for (int i = 0; i < num; i++) {
            booleans[i] = random.nextBoolean();
        }
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] plain() throws IOException {
        return run(getEncoder(Plain.PlainBoolEncoder.class), getDecoder(Plain.PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] bitmap() throws IOException {
        return run(getEncoder(EWAHBitmap.EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmap.EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] bitpacking() throws IOException {
        return run(getEncoder(BitPacking.BitPackingBoolEncoder.class), getDecoder(BitPacking.BitPackingBoolDecoder.class));
    }

    private boolean[] run(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
        return decoder.nextBools(num);
    }
}