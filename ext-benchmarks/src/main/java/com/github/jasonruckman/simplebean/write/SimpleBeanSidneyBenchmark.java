package com.github.jasonruckman.simplebean.write;

import com.github.jasonruckman.simplebean.SimpleBeanBenchmark;
import org.openjdk.jmh.annotations.Benchmark;

public class SimpleBeanSidneyBenchmark extends SimpleBeanBenchmark {
  @Override
  @Benchmark
  public byte[] writeSidney() {
    return doWriteSidney();
  }
}