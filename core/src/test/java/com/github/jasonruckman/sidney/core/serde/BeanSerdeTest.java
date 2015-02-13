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
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeanSerdeTest extends ObjSerdeTest {
  @Test
  public void testPrimitivesBean() throws IOException {
    runTest(new TypeToken<AllPrimitives>() {}, NUM_TO_RUN, new Supplier<AllPrimitives>() {
      @Override
      public AllPrimitives apply() {
        return getDataFactory().newPrimitives();
      }
    });
  }

  @Test
  public void testPrimitiveRefsBean() throws IOException {
    runTest(new TypeToken<AllPrimitiveRefs>(){}, NUM_TO_RUN, new Supplier<AllPrimitiveRefs>() {
      @Override
      public AllPrimitiveRefs apply() {
        return getDataFactory().newPrimitiveRefs();
      }
    });
  }

  @Test
  public void testInheritedPrimitivesBean() throws IOException {
    runTest(new TypeToken<InheritedAllPrimitives>() {
    }, NUM_TO_RUN, new Supplier<InheritedAllPrimitives>() {
      @Override
      public InheritedAllPrimitives apply() {
        return getDataFactory().newInheritedAllPrimitives();
      }
    });
  }

  @Test
  public void testInheritedGenerics() throws IOException {
    runTest(new TypeToken<GenericsContainer<Integer, Double>>() {
    }, NUM_TO_RUN, new Supplier<GenericsContainer<Integer, Double>>() {
      @Override
      public GenericsContainer<Integer, Double> apply() {
        return getDataFactory().newGenericsContainer();
      }
    });
  }
}