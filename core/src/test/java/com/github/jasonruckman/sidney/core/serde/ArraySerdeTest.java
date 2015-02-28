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
package com.github.jasonruckman.sidney.core.serde;

import com.github.jasonruckman.sidney.core.*;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ArraySerdeTest extends ObjSerdeTest {
  @Test
  public void testInts() throws IOException {
    runTest(new TypeToken<int[]>() {
    }, NUM_TO_RUN, new Supplier<int[]>() {
      @Override
      public int[] apply() {
        return getDataFactory().newInts();
      }
    }, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return (Arrays.equals(o1, o2)) ? 0 : -1;
      }
    });
  }

  @Test
  public void testDoubles() throws IOException {
    runTest(new TypeToken<double[]>() {
    }, NUM_TO_RUN, new Supplier<double[]>() {
      @Override
      public double[] apply() {
        return getDataFactory().newDoubles();
      }
    }, new Comparator<double[]>() {
      @Override
      public int compare(double[] o1, double[] o2) {
        return (Arrays.equals(o1, o2)) ? 0 : -1;
      }
    });
  }

  @Test
  public void testArrayOfBeans() throws IOException {
    runTest(new TypeToken<AllPrimitives[]>() {
    }, NUM_TO_RUN, new Supplier<AllPrimitives[]>() {
      @Override
      public AllPrimitives[] apply() {
        AllPrimitives[] primitiveses = new AllPrimitives[getRandom().nextInt(25)];
        for (int i = 0; i < primitiveses.length; i++) {
          primitiveses[i] = getDataFactory().newPrimitives();
        }
        return primitiveses;
      }
    }, new Comparator<AllPrimitives[]>() {
      @Override
      public int compare(AllPrimitives[] o1, AllPrimitives[] o2) {
        return (Arrays.equals(o1, o2)) ? 0 : -1;
      }
    });
  }

  @Test
  public void testNestedPrimitiveArrays() throws IOException {
    runTest(new TypeToken<AllPrimitiveArrays>() {
    }, NUM_TO_RUN, new Supplier<AllPrimitiveArrays>() {
      @Override
      public AllPrimitiveArrays apply() {
        return getDataFactory().newAllArrays();
      }
    });
  }

  @Test
  public void testPrimitiveRefArrays() throws IOException {
    runTest(new TypeToken<AllPrimitiveRefsArrays>() {
    }, NUM_TO_RUN, new Supplier<AllPrimitiveRefsArrays>() {
      @Override
      public AllPrimitiveRefsArrays apply() {
        return getDataFactory().newAllPrimitiveRefArrays();
      }
    });
  }
}