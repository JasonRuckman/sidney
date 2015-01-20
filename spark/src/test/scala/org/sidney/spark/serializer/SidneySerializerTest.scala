package org.sidney.spark.serializer

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel
import org.junit.Test

class SidneySerializerTest {
  @Test
  def testBeans(): Unit = {
    val ints = Array(1, 2, 3, 4)
    val conf = new SparkConf()
    conf.set("spark.serializer", classOf[SidneySerializer].getName)
    val sc = new SparkContext("local", "test", conf)
    val t = sc.parallelize(ints).map(x => x).persist(StorageLevel.MEMORY_ONLY_SER).collect()
  }
}
