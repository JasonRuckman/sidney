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

import com.github.jasonruckman.sidney.core.AllPrimitiveRefs;
import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapSerdeTest extends ObjSerdeTest {
  @Test
  public void testIntToIntMap() throws IOException {
    runTest(new TypeToken<Map<Integer, Integer>>() {
    }, NUM_TO_RUN, new Supplier<Map<Integer, Integer>>() {
      @Override
      public Map<Integer, Integer> apply() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < getRandom().nextInt(256); i++) {
          map.put(getRandom().nextInt(), getRandom().nextInt());
        }
        return map;
      }
    });
  }

  @Test
  public void testBeanToBeanMap() throws IOException {
    runTest(new TypeToken<Map<AllPrimitiveRefs, AllPrimitiveRefs>>() {
    }, NUM_TO_RUN, new Supplier<Map<AllPrimitiveRefs, AllPrimitiveRefs>>() {
      @Override
      public Map<AllPrimitiveRefs, AllPrimitiveRefs> apply() {
        Map<AllPrimitiveRefs, AllPrimitiveRefs> map = new HashMap<>();
        for (int i = 0; i < getRandom().nextInt(256); i++) {
          map.put(getDataFactory().newPrimitiveRefs(), getDataFactory().newPrimitiveRefs());
        }
        return map;
      }
    });
  }
}
