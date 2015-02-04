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

import com.github.jasonruckman.sidney.core.AllPrimitiveRefs
import com.github.jasonruckman.sidney.core.serde.SerdeTestBase
import com.github.jasonruckman.sidney.scala.ScalaSid
import org.junit.{Assert, Test}

class ScalaMapSerdeTest extends SerdeTestBase {
  private val sid = new ScalaSid

  @Test
  def testIntToIntMap() {
    val map = new util.HashMap[Int, Int]()
    var i = 0
    while (i < getRandom.nextInt(128)) {
      map.put(getDataFactory.newInt, getDataFactory.newInt)
      i += 1
    }
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[util.Map[Int, Int]]()
    writer.open(baos)
    writer.write(map)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[util.Map[Int, Int]]
    reader.open(bais)
    val outMap = if (reader.hasNext) reader.read else null
    Assert.assertEquals(map, outMap)
  }

  @Test
  def testBeanToBeanMap() {
    val map = getDataFactory.newMaps

    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[java.util.Map[AllPrimitiveRefs, AllPrimitiveRefs]]()
    writer.open(baos)
    writer.write(map)
    writer.close()
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[java.util.Map[AllPrimitiveRefs, AllPrimitiveRefs]]()
    reader.open(bais)
    val out= if ((reader.hasNext)) reader.read else null
    Assert.assertEquals(map, out)
  }
}
