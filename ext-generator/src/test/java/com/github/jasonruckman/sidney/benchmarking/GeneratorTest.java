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
package com.github.jasonruckman.sidney.benchmarking;

import com.github.jasonruckman.sidney.generator.BeanBuilder;
import com.github.jasonruckman.sidney.generator.Generator;
import org.junit.Test;

import static com.github.jasonruckman.sidney.generator.Strategies.*;


public class GeneratorTest {
  @Test
  public void test() {
    BeanBuilder<TestBean> b = BeanBuilder.stub(TestBean.class);
    TestBean bean = b.getStub();
    Generator<TestBean> beanGenerator = b
        .field(bean.isThird(), randomBool())
        .field(bean.getFirst(), randomInt())
        .field(bean.getSecond(), randomLong()).build();

    beanGenerator.list(100);
  }
}