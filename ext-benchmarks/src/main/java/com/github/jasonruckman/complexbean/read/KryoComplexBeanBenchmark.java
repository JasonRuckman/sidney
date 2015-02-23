package com.github.jasonruckman.complexbean.read;

import com.github.jasonruckman.complexbean.ComplexBeanBenchmark;
import com.github.jasonruckman.model.NestedBean;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;

public class KryoComplexBeanBenchmark extends ComplexBeanBenchmark {
  @Override
  @Benchmark
  public List<NestedBean> readKryo() {
    return doReadKryo();
  }
}
