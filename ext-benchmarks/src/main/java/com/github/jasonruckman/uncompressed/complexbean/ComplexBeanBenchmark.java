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
package com.github.jasonruckman.uncompressed.complexbean;

import com.github.jasonruckman.uncompressed.AbstractBenchmark;
import com.github.jasonruckman.model.BeansWithPrimitives;
import com.github.jasonruckman.model.ComplexKey;
import com.github.jasonruckman.model.NestedBean;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.type.Type;
import com.github.jasonruckman.sidney.generator.BeanBuilder;
import com.github.jasonruckman.sidney.generator.Generator;
import com.github.jasonruckman.sidney.generator.MapGenerator;

import java.util.List;

import static com.github.jasonruckman.sidney.generator.Strategies.*;

public class ComplexBeanBenchmark extends AbstractBenchmark<NestedBean> {
  public ComplexBeanBenchmark() {
    super(new TypeToken<NestedBean>() {});
  }

  @Override
  public List<NestedBean> sampleData() {
    BeanBuilder<ComplexKey> keyBuilder = BeanBuilder.stub(ComplexKey.class);
    ComplexKey keyStub = keyBuilder.getStub();

    Generator<ComplexKey> keyGenerator = keyBuilder
        .field(keyStub.getFirst(), incrementingBy(1))
        .field(keyStub.getSecond(), range(0.5, 0.7)).build();

    BeanBuilder<BeansWithPrimitives> beansWithPrimitivesGenerator = BeanBuilder.stub(BeansWithPrimitives.class);
    BeansWithPrimitives bwp = beansWithPrimitivesGenerator.getStub();
    Generator<BeansWithPrimitives> bwpGenerator = beansWithPrimitivesGenerator
        .field(bwp.isFirst(), percentageTrue(0.7))
        .field(bwp.getSecond(), range(0L, 500L))
        .field(bwp.getThird(), incrementingBy(1L))
        .field(bwp.getFourth(), range(0, 5))
        .field(bwp.getFifth(), always(0.5D)).build();

    MapGenerator<ComplexKey, BeansWithPrimitives> generator = new MapGenerator<>(
        range(0, 500), keyGenerator, bwpGenerator
    );

    BeanBuilder<NestedBean> builder = BeanBuilder.stub(NestedBean.class);
    NestedBean stub = builder.getStub();
    Generator<NestedBean> nbGenerator = builder.field(stub.getFirst(), generator)
        .field(stub.getSecond(), always(10))
        .field(stub.getThird(), randomInt())
        .field(stub.getFourth(), randomFloat()).build();

    return nbGenerator.list(500);
  }
}
