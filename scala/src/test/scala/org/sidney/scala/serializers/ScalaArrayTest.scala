package org.sidney.scala.serializers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import org.junit.{Assert, Test}
import org.sidney.scala.ScalaSid

class ScalaArrayTest {
  @Test
  def testIntArrays(): Unit = {
    val arr = Array(1, 2, 3)
    val scalaSid = new ScalaSid

    val writer = scalaSid.newWriter[Array[Int]]()
    val baos = new ByteArrayOutputStream()
    writer.open(baos)
    writer.write(arr)
    writer.close()

    val reader = scalaSid.newReader[Array[Int]]()
    val bais = new ByteArrayInputStream(baos.toByteArray)
    reader.open(bais)
    val out = if(reader.hasNext) reader.read() else null

    Assert.assertArrayEquals(arr, out)
  }
}
