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
package com.github.jasonruckman.sidney.scala.serializers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util
import java.util.List

import com.github.jasonruckman.sidney.core.AllPrimitives
import com.github.jasonruckman.sidney.core.serde.SerdeTestBase
import com.github.jasonruckman.sidney.scala.ScalaSid
import org.junit.{Assert, Test}

class ScalaCollectionSerdeTest extends SerdeTestBase {
  private val sid = new ScalaSid

  @Test
  def testListOfBeans() {
    val one: AllPrimitives = getDataFactory.newPrimitives
    val two: AllPrimitives = getDataFactory.newPrimitives
    val list = new util.ArrayList[AllPrimitives]
    list.add(one)
    list.add(two)
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[java.util.List[AllPrimitives]]
    writer.open(baos)
    writer.write(list)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[java.util.List[AllPrimitives]]
    reader.open(bais)
    val output = if ((reader.hasNext)) reader.read else null
    Assert.assertEquals(list, output)
  }

  @Test
  def testManyListsOfBeans() {
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[java.util.List[AllPrimitives]]
    writer.open(baos)
    val lists = new util.ArrayList[List[AllPrimitives]]
    val num: Int = 1025
    Range(0, num).foreach(f => {
      val prims = new util.ArrayList[AllPrimitives]()
      Range(0, getDataFactory.newByte()).foreach(f => prims.add(getDataFactory.newPrimitives()))
    })

    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[java.util.List[AllPrimitives]]
    reader.open(bais)
    val out = new util.ArrayList[List[AllPrimitives]]
    while (reader.hasNext) {
      out.add(reader.read)
    }
    Assert.assertEquals(lists, out)
  }
}
