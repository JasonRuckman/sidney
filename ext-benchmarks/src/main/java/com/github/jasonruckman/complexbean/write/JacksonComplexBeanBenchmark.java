package com.github.jasonruckman.complexbean.write;

import com.github.jasonruckman.complexbean.ComplexBeanBenchmark;
import com.github.jasonruckman.model.NestedBean;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;

public class JacksonComplexBeanBenchmark extends ComplexBeanBenchmark {
  @Override
  @Benchmark
  public byte[] writeJackson() {
    return doWriteJackson();
  }
}
