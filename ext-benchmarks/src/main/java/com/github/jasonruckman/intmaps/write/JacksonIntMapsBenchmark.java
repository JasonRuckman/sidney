package com.github.jasonruckman.intmaps.write;

import com.github.jasonruckman.intmaps.IntMapsBenchmark;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;
import java.util.Map;

public class JacksonIntMapsBenchmark extends IntMapsBenchmark {
  @Override
  @Benchmark
  public byte[] writeJackson() {
    return doWriteJackson();
  }
}
