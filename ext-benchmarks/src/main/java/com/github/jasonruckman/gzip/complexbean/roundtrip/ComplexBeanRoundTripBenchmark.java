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
package com.github.jasonruckman.gzip.complexbean.roundtrip;

import com.github.jasonruckman.gzip.complexbean.ComplexBeanBenchmark;
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