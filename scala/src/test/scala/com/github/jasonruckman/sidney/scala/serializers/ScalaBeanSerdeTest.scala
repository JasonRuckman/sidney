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

import com.github.jasonruckman.sidney.core.serde.SerdeTestBase
import com.github.jasonruckman.sidney.scala.ScalaSid
import org.junit.{Assert, Test}

class ScalaBeanSerdeTest extends SerdeTestBase {
  private val sid = new ScalaSid

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
      if (record != null) {
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

  private def arrayEquals[T](left: Array[T], right: Array[T]) = {
    if (left != right || left.length != right.length) {
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
