package com.github.jasonruckman.simplebean;

import com.github.jasonruckman.AbstractBenchmark;
import com.github.jasonruckman.model.SimpleBean;
import com.github.jasonruckman.sidney.core.type.TypeToken;
import com.github.jasonruckman.sidney.generator.BeanBuilder;
import com.github.jasonruckman.sidney.generator.Generator;

import static com.github.jasonruckman.sidney.generator.Strategies.*;
import java.util.List;

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
