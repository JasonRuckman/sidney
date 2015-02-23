package com.github.jasonruckman.simplebean.write;

import com.github.jasonruckman.simplebean.SimpleBeanBenchmark;
import org.openjdk.jmh.annotations.Benchmark;

public class SimpleBeanUnsafeSidneyBenchmark extends SimpleBeanBenchmark {
  @Override
  @Benchmark
  public byte[] writeUnsafeSidney() {
    return doUnsafeWriteSidney();
  }
}