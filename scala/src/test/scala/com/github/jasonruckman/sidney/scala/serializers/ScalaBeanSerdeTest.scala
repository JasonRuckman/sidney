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

import com.github.jasonruckman.sidney.core._
import com.github.jasonruckman.sidney.core.serde.{Reader, SerdeTestBase, Writer}
import org.junit.{Assert, Test}

class ScalaBeanSerdeTest extends SerdeTestBase {
  private val sid = new ScalaSid

  @Test
  def testPrimitivesBean {
    val one: AllPrimitives = getDataFactory.newPrimitives
    val two: AllPrimitives = getDataFactory.newPrimitives
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val sid: Sid = new Sid
    val writer: Writer[AllPrimitives] = sid.newWriter(classOf[AllPrimitives])
    writer.open(baos)
    writer.write(one)
    writer.write(two)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader: Reader[AllPrimitives] = sid.newReader(classOf[AllPrimitives])
    reader.open(bais)
    val out: AllPrimitives = if ((reader.hasNext)) reader.read else null
    val outTwo: AllPrimitives = if ((reader.hasNext)) reader.read else null
    Assert.assertEquals(one, out)
    Assert.assertEquals(two, outTwo)
  }

  @Test
  def testPrimitiveRefsBean {
    val one: AllPrimitiveRefs = getDataFactory.newPrimitiveRefs
    val two: AllPrimitiveRefs = getDataFactory.newPrimitiveRefs
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val sid: Sid = new Sid
    val writer: Writer[AllPrimitiveRefs] = sid.newWriter(classOf[AllPrimitiveRefs])
    writer.open(baos)
    writer.write(one)
    writer.write(two)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader: Reader[AllPrimitiveRefs] = sid.newReader(classOf[AllPrimitiveRefs])
    reader.open(bais)
    val out: AllPrimitiveRefs = if ((reader.hasNext)) reader.read else null
    val outTwo: AllPrimitiveRefs = if ((reader.hasNext)) reader.read else null
    Assert.assertEquals(one, out)
    Assert.assertEquals(two, outTwo)
  }

  @Test
  def testInheritedPrimitivesBean {
    val one: InheritedAllPrimitives = getDataFactory.newInheritedAllPrimitives
    val two: InheritedAllPrimitives = getDataFactory.newInheritedAllPrimitives
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val sid: Sid = new Sid
    val writer: Writer[InheritedAllPrimitives] = sid.newWriter(classOf[InheritedAllPrimitives])
    writer.open(baos)
    writer.write(one)
    writer.write(two)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader: Reader[InheritedAllPrimitives] = sid.newReader(classOf[InheritedAllPrimitives])
    reader.open(bais)
    val out: InheritedAllPrimitives = if ((reader.hasNext)) reader.read else null
    val outTwo: InheritedAllPrimitives = if ((reader.hasNext)) reader.read else null
    Assert.assertEquals(one, out)
    Assert.assertEquals(two, outTwo)
  }

  @Test
  def testInheritedGenerics {
    val one = getDataFactory.newGenericsContainer
    val two = getDataFactory.newGenericsContainer
    val baos = new ByteArrayOutputStream
    val writer = sid.newWriter[GenericsContainer[Integer, java.lang.Double]]
    writer.open(baos)
    writer.write(one)
    writer.write(two)
    writer.close
    val bais = new ByteArrayInputStream(baos.toByteArray)
    val reader  = sid.newReader[GenericsContainer[Integer, java.lang.Double]]
    reader.open(bais)
    val outOne = if (reader.hasNext) reader.read else null
    val outTwo = if (reader.hasNext) reader.read else null
    Assert.assertEquals(one, outOne)
    Assert.assertEquals(two, outTwo)
  }

  @Test
  def testManyPrimitives {
    val baos = new ByteArrayOutputStream
    val num = 4097
    val writer = sid.newWriter[AllPrimitives]
    writer.open(baos)
    val list = new util.ArrayList[AllPrimitives]
    val out = new util.ArrayList[AllPrimitives]
    Range(0, num).foreach(f => {
      val record: AllPrimitives = getDataFactory.newPrimitives
      writer.write(record)
      list.add(record)
    })
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[AllPrimitives]
    reader.open(bais)
    Range(0, num).map(f => {
      if (reader.hasNext) {
        out.add(reader.read)
      }
    })
    val expected = list.toArray(new Array[AllPrimitives](list.size))
    val actual = out.toArray(new Array[AllPrimitives](out.size))
    arrayEquals(expected, actual)
  }

  @Test
  def testManyScalaPrimitives {
    val baos = new ByteArrayOutputStream
    val num = 4097
    val writer = sid.newWriter[AllScalaPrimitives]
    writer.open(baos)
    val list = new util.ArrayList[AllScalaPrimitives]
    val out = new util.ArrayList[AllScalaPrimitives]
    Range(0, num).foreach(f => {
      val record = getDataFactory.newPrimitives
      var newRecord = new AllScalaPrimitives
      if(record != null) {
        newRecord.first = record.isFirst
        newRecord.second = record.getFifth
        newRecord.third = record.getFourth
        newRecord.fourth = record.getThird
        newRecord.fifth = record.getSecond
        newRecord.sixth = record.getSixth
        newRecord.seventh = record.getSeventh
        newRecord.eighth = record.getEighth
      } else {
        newRecord = null
      }
      writer.write(newRecord)
      list.add(newRecord)
    })
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[AllScalaPrimitives]
    reader.open(bais)
    Range(0, num).map(f => {
      if (reader.hasNext) {
        out.add(reader.read)
      }
    })
    val expected = list.toArray
    val actual = out.toArray
    arrayEquals(expected, actual)
  }

  private def arrayEquals[T](left : Array[T], right : Array[T]) = {
    if(left != right || left.length != right.length) {
      false
    } else {
      var len = left.length
      var counter = 0
      while (counter < len) {
        Assert.assertEquals(left(counter), right(counter))
        counter += 1
      }
    }
  }
}
