package com.github.jasonruckman.sidney.ext.guava;

import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
import com.github.jasonruckman.sidney.core.serde.ObjSerdeTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.junit.Test;

import java.io.IOException;

public class GuavaTest extends ObjSerdeTest {
  @Test
  public void immutableListTest() throws IOException {
    runTest(new TypeToken<ImmutableList<Integer>>() {
    }, NUM_TO_RUN, new Supplier<ImmutableList<Integer>>() {
      @Override
      public ImmutableList<Integer> apply() {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        for(int i = 0; i < getRandom().nextInt(256); i++) {
          builder.add(i);
        }
        return builder.build();
      }
    }, GuavaExtensions.add(newSid()));
  }

  @Test
  public void multimapTest() throws IOException {
    runTest(new TypeToken<Multimap<Integer, Integer>>() {
    }, NUM_TO_RUN, new Supplier<Multimap<Integer, Integer>>() {
      @Override
      public Multimap<Integer, Integer> apply() {
        Multimap<Integer, Integer> map = ArrayListMultimap.create();
        for(int i = 0; i < getRandom().nextInt(256); i++) {
          for(int j = 0; j < getRandom().nextInt(5); j++) {
            map.put(i, j);
          }
        }
        return map;
      }
    }, GuavaExtensions.add(newSid()));
  }

  @Test
  public void unsignedIntTest() throws IOException {
    runTest(new TypeToken<UnsignedInteger>() {
    }, NUM_TO_RUN, new Supplier<UnsignedInteger>() {
      @Override
      public UnsignedInteger apply() {
        return UnsignedInteger.valueOf(getRandom().nextInt(1024000));
      }
    }, GuavaExtensions.add(newSid()));
  }

  @Test
  public void unsignedLongTest() throws IOException {
    runTest(new TypeToken<UnsignedLong>() {
    }, NUM_TO_RUN, new Supplier<UnsignedLong>() {
      @Override
      public UnsignedLong apply() {
        return UnsignedLong.valueOf(getRandom().nextInt(1024000));
      }
    }, GuavaExtensions.add(newSid()));
  }
}
