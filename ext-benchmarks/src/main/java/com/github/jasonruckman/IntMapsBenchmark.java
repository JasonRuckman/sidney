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
package com.github.jasonruckman;

import com.github.jasonruckman.sidney.core.TypeToken;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.serde.Type;
import com.github.jasonruckman.sidney.generator.MapGenerator;
import com.github.jasonruckman.sidney.generator.Strategies;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Map;

@State(Scope.Group)
@Warmup(iterations = 20)
@Measurement(iterations = 1)
@Fork(1)
public class IntMapsBenchmark extends AbstractBenchmark<Map<Integer, Integer>> {
  public static final String BENCHMARK_NAME = "IntIntMapIncreasingKeysRangeValues";
  public static final String BENCHMARK_NAME_GZIP = "IntIntMapIncreasingKeysRangeValuesGZIP";

  public IntMapsBenchmark() {
    super(new TypeToken<Map<Integer, Integer>>() {});

    getSid().overrideDefaultEncoding(Type.INT32, Encoding.DELTABITPACKINGHYBRID);
  }


  @Override
  public List<Map<Integer, Integer>> sampleData() {
    MapGenerator<Integer, Integer> mapGenerator = new MapGenerator<>(
        Strategies.range(5, 50), Strategies.incrementingBy(1), Strategies.range(1, Short.MAX_VALUE)
    );

    return mapGenerator.list(5000);
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readSidney() {
    return doReadSidney();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readJackson() {
    return doReadJackson();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readKryo() {
    return doReadKryo();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeSidney() {
    return doWriteSidney();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeJackson() {
    return doWriteJackson();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeKryo() {
    return doWriteKryo();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readSidneyGZIP() {
    return doReadSidneyGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readJacksonGZIP() {
    return doReadJacksonGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<Map<Integer, Integer>> readKryoGZIP() {
    return doReadKryoGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeSidneyGZIP() {
    return doWriteSidneyGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeJacksonGZIP() {
    return doWriteJacksonGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeKryoGZIP() {
    return doWriteKryoGZIP();
  }
}