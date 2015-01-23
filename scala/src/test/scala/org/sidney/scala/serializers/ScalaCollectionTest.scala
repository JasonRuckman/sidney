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
import java.util

import org.junit.{Assert, Test}
import org.sidney.scala.ScalaSid

class ScalaCollectionTest {
  @Test
  def testIntList(): Unit = {
    val arr = new util.ArrayList[Int]()
    val scalaSid = new ScalaSid

    val writer = scalaSid.newWriter[util.ArrayList[Int]](classOf[Int])
    val baos = new ByteArrayOutputStream()
    writer.open(baos)
    writer.write(arr)
    writer.close()

    val reader = scalaSid.newReader[util.ArrayList[Int]](classOf[Int])
    val bais = new ByteArrayInputStream(baos.toByteArray)
    reader.open(bais)
    val out = if (reader.hasNext) reader.read() else null

    Assert.assertEquals(arr, out)
  }
}
