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
import com.github.jasonruckman.sidney.core.io.int32.*;
import org.openjdk.jmh.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 2)
@Fork(1)
public class Int32EncoderBenchmarks extends BenchmarkingBase {
  private final int[] ints;
  private int num = 65536;

  public Int32EncoderBenchmarks() {
    ints = new int[num];
    Random random = new Random(11L);
    for (int i = 0; i < ints.length; i++) {
      ints[i] = random.nextInt(65536);
    }
  }

  @Benchmark
  @Group("intEncoders")
  public int[] deltaBitPackingHybrid() throws IOException {
    return run(getEncoder(DeltaBitPacking.DeltaBitPackingInt32Encoder.class), getDecoder(DeltaBitPacking.DeltaBitPackingInt32Decoder.class));
  }

  @Benchmark
  @Group("intEncoders")
  public int[] plain() throws IOException {
    return run(getEncoder(Plain.PlainInt32Encoder.class), getDecoder(Plain.PlainInt32Decoder.class));
  }

  @Benchmark
  @Group("intEncoders")
  public int[] bitpacking() throws IOException {
    return run(getEncoder(BitPacking.BitPackingInt32Encoder.class), getDecoder(BitPacking.BitPackingInt32Decoder.class));
  }

  private int[] run(Int32Encoder encoder, Int32Decoder decoder) throws IOException {
    for (int i : ints) {
      encoder.writeInt(i);
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    encoder.writeToStream(baos);
    encoder.reset();
    decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
    return decoder.nextInts(num);
  }
}
