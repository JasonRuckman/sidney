package com.github.jasonruckman.complexbean.read;

import com.github.jasonruckman.complexbean.ComplexBeanBenchmark;
import com.github.jasonruckman.model.NestedBean;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;

public class SidneyComplexBeanBenchmark extends ComplexBeanBenchmark {
  @Override
  @Benchmark
  public List<NestedBean> readSidney() {
    return doReadSidney();
  }
}
