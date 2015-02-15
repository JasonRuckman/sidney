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

import com.github.jasonruckman.sidney.core.AllPrimitives;
import com.github.jasonruckman.sidney.core.JavaSid;
import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ReferenceTrackingTest extends ObjSerdeTest {
  @Test
  public void testSimpleReferences() throws IOException {
    runTest(new TypeToken<AllPrimitives>() {
    }, 5000, new Supplier<AllPrimitives>() {
      @Override
      public AllPrimitives apply() {
        return getDataFactory().newPrimitives();
      }
    }, newSidReferences());
  }

  @Test
  public void testWithNonCircularReferences() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream gos = new GZIPOutputStream(baos);
    JavaSid sid = new JavaSid();
    sid.setReferences(true);

    Writer<Map<Integer, AllPrimitives>> mapWriter = sid.newWriter(new TypeToken<Map<Integer, AllPrimitives>>() {
    });

    AllPrimitives record = getDataFactory().newPrimitives();
    while (record == null) {
      record = getDataFactory().newPrimitives();
    }
    Map<Integer, AllPrimitives> map = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      map.put(i, record);
    }

    mapWriter.open(gos);
    mapWriter.write(map);
    mapWriter.close();
    gos.close();

    InputStream bais = new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray()));
    Reader<Map<Integer, AllPrimitives>> mapReader = sid.newReader(new TypeToken<Map<Integer, AllPrimitives>>() {
    });
    mapReader.open(bais);

    List<Map<Integer, AllPrimitives>> list = mapReader.readAll();
    mapReader.close();
    AllPrimitives first = list.get(0).get(1);
    for (Map.Entry<?, AllPrimitives> entry : list.get(0).entrySet()) {
      Assert.assertSame(first, entry.getValue());
    }
  }
}