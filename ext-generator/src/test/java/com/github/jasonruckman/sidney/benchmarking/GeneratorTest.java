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