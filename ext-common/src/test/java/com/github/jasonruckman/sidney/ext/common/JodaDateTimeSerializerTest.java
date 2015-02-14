package com.github.jasonruckman.sidney.ext.common;

import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
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
