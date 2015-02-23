package com.github.jasonruckman.simplebean.write;

import com.github.jasonruckman.simplebean.SimpleBeanBenchmark;
import org.openjdk.jmh.annotations.Benchmark;

public class SimpleBeanKryoBenchmark extends SimpleBeanBenchmark {
  @Override
  @Benchmark
  public byte[] writeKryo() {
    return doWriteKryo();
  }
}
