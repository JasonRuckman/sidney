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
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class JustPrimitivesTest {
  @Test
  public void testBytes() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Primitives.ByteWriter writer = sid.newByteWriter();
    writer.open(baos);
    byte[] bytes = new byte[100];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) i;
      writer.writeByte((byte) i);
    }
    writer.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Primitives.ByteReader reader = sid.newByteReader();
    reader.open(bais);
    int counter = 0;
    while (reader.hasNext()) {
      Assert.assertEquals(bytes[counter++], reader.readByte());
    }
  }

  @Test
  public void testShorts() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Primitives.ShortWriter writer = sid.newShortWriter();
    writer.open(baos);
    short[] shorts = new short[100];
    for (int i = 0; i < shorts.length; i++) {
      shorts[i] = (short) i;
      writer.writeShort((short) i);
    }
    writer.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Primitives.ShortReader reader = sid.newShortReader();
    reader.open(bais);
    int counter = 0;
    while (reader.hasNext()) {
      Assert.assertEquals(shorts[counter++], reader.readShort());
    }
  }

  @Test
  public void testInts() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    JavaSid sid = new JavaSid();
    Primitives.IntWriter writer = sid.newIntWriter();
    writer.open(baos);
    int[] ints = new int[100];
    for (int i = 0; i < ints.length; i++) {
      ints[i] = i;
      writer.writeInt(i);
    }
    writer.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    Primitives.IntReader reader = sid.newIntReader();
    reader.open(bais);
    int counter = 0;
    while (reader.hasNext()) {
      Assert.assertEquals(ints[counter++], reader.readInt());
    }
  }
}