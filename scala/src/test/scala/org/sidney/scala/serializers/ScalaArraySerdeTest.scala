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
package org.sidney.scala.serializers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.junit.{Test, Assert}
import org.sidney.core.serde.{Writer, Reader, SerdeTestBase}
import org.sidney.core.{AllPrimitives, AllPrimitiveRefsArrays, AllPrimitiveArrays, Sid}
import org.sidney.scala.ScalaSid

class ScalaArraySerdeTest extends SerdeTestBase {
  val sid = new ScalaSid

  @Test
  def testInts() {
    val ints: Array[Int] = getDataFactory.newInts
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[Array[Int]]()
    writer.open(baos)
    writer.write(ints)
    writer.write(ints)
    writer.close
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader: Reader[Array[Int]] = sid.newReader[Array[Int]]
    reader.open(bais)
    val output: Array[Int] = if (reader.hasNext) reader.read else null
    arrayEquals(ints, output)
  }

  @Test
  def testArrayOfBeans() {
    val primitiveses = Array[AllPrimitives](getDataFactory.newPrimitives, getDataFactory.newPrimitives)
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[Array[AllPrimitives]]
    writer.open(baos)
    writer.write(primitiveses)
    writer.close()
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[Array[AllPrimitives]]
    reader.open(bais)
    val output = if (reader.hasNext) reader.read else null
    arrayEquals(primitiveses, output)
  }

  @Test
  def testNestedPrimitiveArrays() {
    val arrays = getDataFactory.newAllArrays
    val baos = new ByteArrayOutputStream
    val writer = sid.newWriter[AllPrimitiveArrays]
    writer.open(baos)
    writer.write(arrays)
    writer.close
    val bais = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[AllPrimitiveArrays]
    reader.open(bais)
    val output = if (reader.hasNext) reader.read else null
    Assert.assertEquals(arrays, output)
  }

  @Test
  def testPrimitiveRefArrays() {
    val one: AllPrimitiveRefsArrays = getDataFactory.newAllPrimitiveRefArrays
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[AllPrimitiveRefsArrays]
    writer.open(baos)
    writer.write(one)
    writer.close()
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[AllPrimitiveRefsArrays]()
    reader.open(bais)
    val out: AllPrimitiveRefsArrays = if (reader.hasNext) reader.read else null
    Assert.assertEquals(one, out)
  }

  @Test
  def testNestedScalaArrays() {
    val one = new NestedScalaArrays[AllPrimitives]
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    val writer = sid.newWriter[NestedScalaArrays[AllPrimitives]]
    writer.open(baos)
    writer.write(one)
    writer.close()
    val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
    val reader = sid.newReader[NestedScalaArrays[AllPrimitives]]()
    reader.open(bais)
    val out = if (reader.hasNext) reader.read else null
    Assert.assertEquals(one, out)
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
