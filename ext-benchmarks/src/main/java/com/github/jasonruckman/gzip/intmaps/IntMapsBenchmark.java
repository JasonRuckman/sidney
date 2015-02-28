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
package com.github.jasonruckman.gzip.intmaps;

import com.github.jasonruckman.gzip.AbstractBenchmark;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.generator.MapGenerator;
import com.github.jasonruckman.sidney.generator.Strategies;

import java.util.HashMap;
import java.util.List;

public class IntMapsBenchmark extends AbstractBenchmark<HashMap<Integer, Integer>> {
  public IntMapsBenchmark() {
    super(new TypeToken<HashMap<Integer, Integer>>() {
    });
  }


  @Override
  public List<HashMap<Integer, Integer>> sampleData() {
    MapGenerator<Integer, Integer> mapGenerator = new MapGenerator<>(
        Strategies.range(5, 50), Strategies.incrementingBy(1), Strategies.range(1, Short.MAX_VALUE)
    );

    return mapGenerator.listHashMaps(5000);
  }
}