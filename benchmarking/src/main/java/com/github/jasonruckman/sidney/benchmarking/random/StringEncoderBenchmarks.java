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
import com.github.jasonruckman.sidney.core.io.string.*;
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
public class StringEncoderBenchmarks extends BenchmarkingBase {
  private final String[] strings;
  private int num = 65536;

  public StringEncoderBenchmarks() {
    strings = new String[num];
    Random random = new Random(11L);
    for (int i = 0; i < num; i++) {
      char[] chars = new char[124];
      for (int j = 0; j < chars.length; j++) {
        chars[j] = (char) random.nextInt(32767);
      }
      strings[i] = new String(chars);
    }
  }

  @Benchmark
  @Group("stringEncodersRandom")
  public String[] plain() throws IOException {
    return run(getEncoder(Plain.PlainStringEncoder.class), getDecoder(Plain.PlainStringDecoder.class));
  }

  @Benchmark
  @Group("stringEncodersRandom")
  public String[] deltaLength() throws IOException {
    return run(getEncoder(DeltaLength.DeltaLengthStringEncoder.class), getDecoder(DeltaLength.DeltaLengthStringDecoder.class));
  }

  @Benchmark
  @Group("stringEncodersRandom")
  public String[] charAsInt() throws IOException {
    return run(getEncoder(CharAsInt.CharAsIntStringEncoder.class), getDecoder(CharAsInt.CharAsIntStringDecoder.class));
  }

  private String[] run(StringEncoder encoder, StringDecoder decoder) throws IOException {
    for (String i : strings) {
      encoder.writeString(i);
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    encoder.writeToStream(baos);
    encoder.reset();
    decoder.readFromStream(new ByteArrayInputStream(baos.toByteArray()));
    return decoder.readStrings(num);
  }
}
