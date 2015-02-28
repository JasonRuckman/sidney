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
package com.github.jasonruckman.lz4.simplebean;

import com.github.jasonruckman.lz4.AbstractBenchmark;
import com.github.jasonruckman.model.SimpleBean;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.generator.BeanBuilder;
import com.github.jasonruckman.sidney.generator.Generator;

import java.util.List;

import static com.github.jasonruckman.sidney.generator.Strategies.*;

public class SimpleBeanBenchmark extends AbstractBenchmark<SimpleBean> {
  public SimpleBeanBenchmark() {
    super(new TypeToken<SimpleBean>() {});
  }

  @Override
  public List<SimpleBean> sampleData() {
    BeanBuilder<SimpleBean> builder = BeanBuilder.stub(SimpleBean.class);
    SimpleBean stub = builder.getStub();

    Generator<SimpleBean> generator = builder
        .field(stub.getFirst(), incrementingBy(8))
        .field(stub.getSecond(), always((short)1))
        .field(stub.getThird(), always(5.0f))
        .field(stub.getFourth(), randomString())
        .field(stub.getFifth(), randomDouble()).build();

    return generator.list(1000);
  }
}
