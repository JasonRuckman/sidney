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
        for(int i = 0; i < getRandom().nextInt(256); i++) {
          if(getRandom().nextBoolean()) {
            bitSet.set(i);
          }
        }
        return bitSet;
      }
    });
  }
}
