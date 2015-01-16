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
import org.sidney.core.Bytes;
import org.sidney.core.encoding.bool.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
    public boolean[] plainEncoderUncompressed() throws IOException {
        return run(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] ewahBoolEncoderUncompressed() throws IOException {
        return run(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingRandom")
    public boolean[] bitPackingEncoderUncompressed() throws IOException {
        return run(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] plainEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(PlainBoolEncoder.class), getDecoder(PlainBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] ewahBoolEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(EWAHBitmapBoolEncoder.class), getDecoder(EWAHBitmapBoolDecoder.class));
    }

    @Benchmark
    @Group("boolEncodingGZIPRandom")
    public boolean[] bitPackingEncoderGZIP() throws IOException {
        return runGZipCompressed(getEncoder(BitPackingBoolEncoder.class), getDecoder(BitPackingBoolDecoder.class));
    }

    private boolean[] run(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encoder.writeToStream(baos);
        decoder.populateBufferFromStream(Bytes.wrapInStream(baos.toByteArray()));
        return decoder.nextBools(num);
    }

    private boolean[] runGZipCompressed(BoolEncoder encoder, BoolDecoder decoder) throws IOException {
        encoder.reset();
        encoder.writeBools(booleans);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(baos);
        encoder.writeToStream(gos);
        gos.close();
        decoder.populateBufferFromStream(
                new BufferedInputStream(new GZIPInputStream(
                        Bytes.wrapInStream(baos.toByteArray()))
                )
        );
        return decoder.nextBools(num);
    }
}