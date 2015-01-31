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
import org.sidney.core.io.int64.GroupVarInt;
import org.sidney.core.io.int64.Int64Decoder;
import org.sidney.core.io.int64.Int64Encoder;
import org.sidney.core.io.int64.Plain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Threads(1)
@Fork(1)
public class Int64EncoderBenchmarks extends BenchmarkingBase {
    private final int num = 65536;
    private final long[] longs;

    public Int64EncoderBenchmarks() {
        longs = new long[num];
        Random random = new Random(11L);
        for (int i = 0; i < longs.length; i++) {
            longs[i] = random.nextLong();
        }
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] plain() throws IOException {
        return run(getEncoder(Plain.PlainInt64Encoder.class), getDecoder(Plain.PlainInt64Decoder.class));
    }

    @Benchmark
    @Group("int64Encoders")
    public long[] groupVarInt() throws IOException {
        return run(getEncoder(GroupVarInt.GroupVarInt64Encoder.class), getDecoder(GroupVarInt.GroupVarInt64Decoder.class));
    }

    private long[] run(Int64Encoder encoder, Int64Decoder decoder) throws IOException {
        for (long i : longs) {
            encoder.writeLong(i);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        encoder.reset();
        decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
        return decoder.nextLongs(num);
    }
}