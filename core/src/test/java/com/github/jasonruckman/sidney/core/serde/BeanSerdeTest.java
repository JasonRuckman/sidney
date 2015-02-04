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

import com.github.jasonruckman.sidney.core.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BeanSerdeTest extends SerdeTestBase {
  @Test
  public void testPrimitivesBean() {
    AllPrimitives one = getDataFactory().newPrimitives();
    AllPrimitives two = getDataFactory().newPrimitives();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    JavaSid sid = new JavaSid();
    Writer<AllPrimitives> writer = sid.newWriter(new TypeToken<AllPrimitives>() {
    });
    writer.open(baos);
    writer.write(one);
    writer.write(two);
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<AllPrimitives> reader = sid.newReader(new TypeToken<AllPrimitives>() {
    });
    reader.open(bais);

    AllPrimitives out = (reader.hasNext()) ? reader.read() : null;
    AllPrimitives outTwo = (reader.hasNext()) ? reader.read() : null;

    Assert.assertEquals(one, out);
    Assert.assertEquals(two, outTwo);
  }

  @Test
  public void testPrimitiveRefsBean() {
    AllPrimitiveRefs one = getDataFactory().newPrimitiveRefs();
    AllPrimitiveRefs two = getDataFactory().newPrimitiveRefs();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Writer<AllPrimitiveRefs> writer = sid.newWriter(new TypeToken<AllPrimitiveRefs>() {
    });
    writer.open(baos);
    writer.write(one);
    writer.write(two);
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<AllPrimitiveRefs> reader = sid.newReader(new TypeToken<AllPrimitiveRefs>() {
    });
    reader.open(bais);
    AllPrimitiveRefs out = (reader.hasNext()) ? reader.read() : null;
    AllPrimitiveRefs outTwo = (reader.hasNext()) ? reader.read() : null;

    Assert.assertEquals(one, out);
    Assert.assertEquals(two, outTwo);
  }

  @Test
  public void testInheritedPrimitivesBean() {
    InheritedAllPrimitives one = getDataFactory().newInheritedAllPrimitives();
    InheritedAllPrimitives two = getDataFactory().newInheritedAllPrimitives();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    JavaSid sid = new JavaSid();
    Writer<InheritedAllPrimitives> writer = sid.newWriter(new TypeToken<InheritedAllPrimitives>() {
    });
    writer.open(baos);
    writer.write(one);
    writer.write(two);
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<InheritedAllPrimitives> reader = sid.newReader(new TypeToken<InheritedAllPrimitives>() {
    });
    reader.open(bais);
    InheritedAllPrimitives out = (reader.hasNext()) ? reader.read() : null;
    InheritedAllPrimitives outTwo = (reader.hasNext()) ? reader.read() : null;

    Assert.assertEquals(one, out);
    Assert.assertEquals(two, outTwo);
  }

  @Test
  public void testInheritedGenerics() {
    GenericsContainer<Integer, Double> one = getDataFactory().newGenericsContainer();
    GenericsContainer<Integer, Double> two = getDataFactory().newGenericsContainer();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Writer<GenericsContainer<Integer, Double>> writer = sid.newWriter(
        new TypeToken<GenericsContainer<Integer, Double>>() {
        }
    );
    writer.open(baos);
    writer.write(one);
    writer.write(two);
    writer.close();

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<GenericsContainer<Integer, Double>> reader = sid.newReader(
        new TypeToken<GenericsContainer<Integer, Double>>() {
        }
    );
    reader.open(bais);

    GenericsContainer<Integer, Double> outOne = (reader.hasNext()) ? reader.read() : null;
    GenericsContainer<Integer, Double> outTwo = (reader.hasNext()) ? reader.read() : null;

    Assert.assertEquals(one, outOne);
    Assert.assertEquals(two, outTwo);
  }

  @Test
  public void testManyPrimitives() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int num = 4097;
    JavaSid sid = new JavaSid();
    Writer<AllPrimitives> writer = sid.newWriter(new TypeToken<AllPrimitives>() {
    });
    writer.open(baos);
    List<AllPrimitives> list = new ArrayList<>();
    List<AllPrimitives> out = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      AllPrimitives record = getDataFactory().newPrimitives();
      writer.write(
          record
      );
      list.add(record);
    }
    writer.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Reader<AllPrimitives> reader = sid.newReader(new TypeToken<AllPrimitives>() {
    });
    reader.open(bais);
    for (int i = 0; i < num; i++) {
      if (reader.hasNext()) {
        out.add(reader.read());
      }
    }

    AllPrimitives[] expected = list.toArray(new AllPrimitives[list.size()]);
    AllPrimitives[] actual = out.toArray(new AllPrimitives[out.size()]);

    Assert.assertArrayEquals(expected, actual);
  }
}