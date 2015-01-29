package org.sidney.scala.serializers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util
import java.util.Map

import org.junit.{Assert, Test}
import org.sidney.core.{AllPrimitiveRefs, AllPrimitives}
import org.sidney.core.serde.{Reader, SerdeTestBase}
import org.sidney.scala.ScalaSid

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
