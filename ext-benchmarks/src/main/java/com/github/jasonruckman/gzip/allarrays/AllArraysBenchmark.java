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
package com.github.jasonruckman.gzip.allarrays;

import com.github.jasonruckman.gzip.AbstractBenchmark;
import com.github.jasonruckman.model.AllArrays;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.type.Type;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.generator.Arrays;
import com.github.jasonruckman.sidney.generator.Generator;
import com.github.jasonruckman.sidney.generator.Strategies;

import java.util.ArrayList;
import java.util.List;

public class AllArraysBenchmark extends AbstractBenchmark<AllArrays> {
  public AllArraysBenchmark() {
    super(new TypeToken<AllArrays>() {});

    getSid().overrideDefaultEncoding(Type.BOOLEAN, Encoding.BITMAP);
    getSid().overrideDefaultEncoding(Type.INT16, Encoding.DELTABITPACKINGHYBRID);
    getSid().overrideDefaultEncoding(Type.INT32, Encoding.BITPACKED);
    getSid().overrideDefaultEncoding(Type.INT64, Encoding.GROUPVARINT);
  }

  @Override
  public List<AllArrays> sampleData() {
    List<AllArrays> all = new ArrayList<>();

    Generator<boolean[]> boolArrayGenerator = Arrays.bools(Strategies.range(1, 500), Strategies.percentageTrue(0.9D));
    Generator<short[]> shortArrayGenerator = Arrays.shorts(Strategies.range(1, 500), Strategies.incrementingBy((short)1));
    Generator<char[]> charArrayGenerator = Arrays.chars(Strategies.range(1, 500), Strategies.randomChar());
    Generator<int[]> intArrayGenerator = Arrays.ints(Strategies.range(1, 500), Strategies.range(1, 50));
    Generator<long[]> longArrayGenerator = Arrays.longs(Strategies.range(1, 500), Strategies.range(1, 1000000L));
    Generator<float[]> floatArrayGenerator = Arrays.floats(Strategies.range(1, 500), Strategies.randomFloat());
    Generator<double[]> doubleArrayGenerator = Arrays.doubles(Strategies.range(1, 500), Strategies.incrementingBy(1.0));

    for(int i = 0; i < 5000; i++) {
      AllArrays allArrays = new AllArrays();
      allArrays.setBooleans(boolArrayGenerator.next());
      allArrays.setShorts(shortArrayGenerator.next());
      allArrays.setChars(charArrayGenerator.next());
      allArrays.setInts(intArrayGenerator.next());
      allArrays.setLongs(longArrayGenerator.next());
      allArrays.setFloats(floatArrayGenerator.next());
      allArrays.setDoubles(doubleArrayGenerator.next());

      all.add(allArrays);
    }

    return all;
  }
}
