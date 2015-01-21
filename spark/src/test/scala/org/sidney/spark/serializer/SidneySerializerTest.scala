package org.sidney.spark.serializer

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class SidneySerializerTest {
  @Test
  def testBeans(): Unit = {
    val ints = Range(0, 500).map(x => {
      val bean = new SomeBean
      bean.first = 1
      bean.second = 1
      bean
    }).toArray
    val conf = new SparkConf()
    conf.set("spark.serializer", classOf[SidneySerializer].getName)
    val sc = new SparkContext("local", "test", conf)
    val t = sc.parallelize(ints).persist(StorageLevel.MEMORY_ONLY_SER).collect()

    val i = 0
  }
}
