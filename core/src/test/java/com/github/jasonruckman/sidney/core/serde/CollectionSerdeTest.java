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

import com.github.jasonruckman.sidney.core.AllPrimitives;
import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionSerdeTest extends ObjSerdeTest {
  @Test
  public void testListOfBeans() throws IOException {
    runTest(new TypeToken<List<AllPrimitives>>() {
    }, NUM_TO_RUN, new Supplier<List<AllPrimitives>>() {
      @Override
      public List<AllPrimitives> apply() {
        List<AllPrimitives> list = new ArrayList<>();
        for (int i = 0; i < getRandom().nextInt(256); i++) {
          list.add(getDataFactory().newPrimitives());
        }
        return list;
      }
    });
  }
}