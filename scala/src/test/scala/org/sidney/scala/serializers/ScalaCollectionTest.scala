package org.sidney.scala.serializers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util

import org.junit.{Assert, Test}
import org.sidney.scala.ScalaSid

class ScalaCollectionTest {
  @Test
  def testIntList() : Unit = {
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
    val out = if(reader.hasNext) reader.read() else null

    Assert.assertEquals(arr, out)
  }
}
