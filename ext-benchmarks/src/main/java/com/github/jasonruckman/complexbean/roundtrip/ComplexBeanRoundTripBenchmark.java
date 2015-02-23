package com.github.jasonruckman.complexbean.roundtrip;

import com.github.jasonruckman.complexbean.ComplexBeanBenchmark;
import com.github.jasonruckman.model.NestedBean;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.List;

public class ComplexBeanRoundTripBenchmark extends ComplexBeanBenchmark {
  @Benchmark
  public List<NestedBean> roundTripSidney() {
    return doReadSidney(doWriteSidney());
  }

  @Benchmark
  public List<NestedBean> roundTripKryo() {
    return doReadKryo(doWriteKryo());
  }
}