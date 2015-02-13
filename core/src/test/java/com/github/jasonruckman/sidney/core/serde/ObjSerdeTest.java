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

import com.github.jasonruckman.sidney.core.JavaSid;
import com.github.jasonruckman.sidney.core.Supplier;
import com.github.jasonruckman.sidney.core.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class ObjSerdeTest extends SerdeTestBase {
  public static final int NUM_TO_RUN = 5000;

  public JavaSid newSid() {
    return new JavaSid();
  }

  public JavaSid newSidReferences() {
    JavaSid sid = new JavaSid();
    sid.setReferences(true);
    return sid;
  }

  public <T> void runTest(TypeToken<T> token, int num, Supplier<T> dataSupplier) throws IOException {
    runTest(token, num, dataSupplier, newSid());
  }

  public <T> void runTest(TypeToken<T> token, int num, Supplier<T> dataSupplier, Comparator<T> comparator) throws IOException {
    runTest(token, num, dataSupplier, newSid(), comparator);
  }

  public <T> void runTest(TypeToken<T> token, int num, Supplier<T> dataSupplier, JavaSid sid) throws IOException {
    runTest(token, num, dataSupplier, sid, new Comparator<T>() {
      @Override
      public int compare(T left, T right) {
        if (Objects.equals(left, right)) {
          return 0;
        }
        return -1;
      }
    });
  }

  public <T> void runTest(TypeToken<T> token, int num, Supplier<T> dataSupplier, JavaSid sid, Comparator<T> comparator) throws IOException {
    Writer<T> writer = sid.newWriter(token);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OutputStream os = new GZIPOutputStream(baos);
    writer.open(os);
    List<T> expected = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      T val = dataSupplier.apply();
      writer.write(val);
      expected.add(val);
    }
    writer.close();
    os.close();

    Reader<T> reader = sid.newReader(token);
    reader.open(new GZIPInputStream(new ByteArrayInputStream(baos.toByteArray())));
    List<T> actual = reader.readAll();

    for (int i = 0; i < actual.size(); i++) {
      T left = expected.get(i);
      T right = actual.get(i);

      int result = comparator.compare(left, right);
      if (result != 0) {
        throw new AssertionError(String.format("Expected: %s \nActual: %s", left, right));
      }
    }
  }
}