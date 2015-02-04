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
import com.github.jasonruckman.sidney.core.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CollectionSerdeTest extends SerdeTestBase {
  @Test
  public void testListOfBeans() {
    AllPrimitives one = getDataFactory().newPrimitives();
    AllPrimitives two = getDataFactory().newPrimitives();

    List<AllPrimitives> list = new ArrayList<>();
    list.add(one);
    list.add(two);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Writer<List<AllPrimitives>> writer = sid.newWriter(new TypeToken<List<AllPrimitives>>() {
    });
    writer.open(baos);
    writer.write(list);
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<List<AllPrimitives>> reader = sid.newReader(
        new TypeToken<List<AllPrimitives>>() {
        }
    );
    reader.open(bais);
    List<AllPrimitives> output = (reader.hasNext()) ? reader.read() : null;
    Assert.assertEquals(list, output);
  }

  @Test
  public void testManyListsOfBeans() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Writer<List<AllPrimitives>> writer = sid.newWriter(new TypeToken<List<AllPrimitives>>() {
    });
    writer.open(baos);

    List<List<AllPrimitives>> lists = new ArrayList<>();
    int num = 1025;
    for (int i = 0; i < num; i++) {
      List<AllPrimitives> primitiveses = new ArrayList<>();
      for (int j = 0; j < getDataFactory().newByte(); j++) {
        primitiveses.add(
            getDataFactory().newPrimitives()
        );
      }
      writer.write(primitiveses);
      lists.add(primitiveses);
    }
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<List<AllPrimitives>> reader = sid.newReader(
        new TypeToken<List<AllPrimitives>>() {
        }
    );
    reader.open(bais);

    List<List<AllPrimitives>> out = new ArrayList<>();
    while (reader.hasNext()) {
      out.add(reader.read());
    }
    Assert.assertEquals(lists, out);
  }
}