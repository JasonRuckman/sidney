package com.github.jasonruckman;

import com.github.jasonruckman.model.BeansWithPrimitives;
import com.github.jasonruckman.model.ComplexKey;
import com.github.jasonruckman.model.NestedBean;
import com.github.jasonruckman.sidney.core.TypeToken;
import com.github.jasonruckman.sidney.generator.BeanBuilder;
import com.github.jasonruckman.sidney.generator.Generator;
import com.github.jasonruckman.sidney.generator.MapGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.List;

import static com.github.jasonruckman.sidney.generator.Strategies.*;

@State(Scope.Group)
@Warmup(iterations = 20)
@Measurement(iterations = 1)
@Fork(1)
public class ComplexBeanBenchmark extends AbstractBenchmark<NestedBean> {
  public static final String BENCHMARK_NAME = "ComplexBean";
  public static final String BENCHMARK_NAME_GZIP = "ComplexBeanGZIP";

  public ComplexBeanBenchmark() {
    super(new TypeToken<NestedBean>() {
    });
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

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<NestedBean> readSidney() {
    return doReadSidney();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<NestedBean> readJackson() {
    return doReadJackson();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public List<NestedBean> readKryo() {
    return doReadKryo();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeSidney() {
    return doWriteSidney();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeJackson() {
    return doWriteJackson();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME)
  @GroupThreads(1)
  public byte[] writeKryo() {
    return doWriteKryo();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<NestedBean> readSidneyGZIP() {
    return doReadSidneyGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<NestedBean> readJacksonGZIP() {
    return doReadJacksonGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public List<NestedBean> readKryoGZIP() {
    return doReadKryoGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeSidneyGZIP() {
    return doWriteSidneyGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeJacksonGZIP() {
    return doWriteJacksonGZIP();
  }

  @Override
  @Benchmark
  @Group(BENCHMARK_NAME_GZIP)
  @GroupThreads(1)
  public byte[] writeKryoGZIP() {
    return doWriteKryoGZIP();
  }
}
