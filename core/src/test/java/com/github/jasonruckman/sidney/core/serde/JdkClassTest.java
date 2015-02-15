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

import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
import org.junit.Test;

import java.io.IOException;
import java.util.BitSet;
import java.util.Date;
import java.util.UUID;

public class JdkClassTest extends ObjSerdeTest {
  @Test
  public void testDates() throws IOException {
    runTest(new TypeToken<Date>() {
    }, NUM_TO_RUN, new Supplier<Date>() {
      @Override
      public Date apply() {
        return new Date();
      }
    });
  }

  @Test
  public void testUUID() throws IOException {
    runTest(new TypeToken<UUID>() {
    }, NUM_TO_RUN, new Supplier<UUID>() {
      @Override
      public UUID apply() {
        return UUID.randomUUID();
      }
    });
  }

  @Test
  public void testBitSet() throws IOException {
    runTest(new TypeToken<BitSet>() {
    }, NUM_TO_RUN, new Supplier<BitSet>() {
      @Override
      public BitSet apply() {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < getRandom().nextInt(256); i++) {
          if (getRandom().nextBoolean()) {
            bitSet.set(i);
          }
        }
        return bitSet;
      }
    });
  }
}
