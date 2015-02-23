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
package com.github.jasonruckman.sidney.ext.common;

import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.core.serde.ObjSerdeTest;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;

public class JodaDateTimeSerializerTest extends ObjSerdeTest {
  @Test
  public void testJoda() throws IOException {
    runTest(new TypeToken<DateTime>() {
            }, NUM_TO_RUN, new Supplier<DateTime>() {
          @Override
          public DateTime apply() {
            return maybeMakeNull(DateTime.now());
          }
        },
        CommonExtensions.add(newSid()));
  }
}
