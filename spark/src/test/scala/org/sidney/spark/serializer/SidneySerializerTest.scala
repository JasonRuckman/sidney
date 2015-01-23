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
package org.sidney.spark.serializer

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Assert, Test}

class SidneySerializerTest {
  private val size = 65536
  val conf = new SparkConf()
  conf.set("spark.serializer", classOf[SidneySerializer].getName)

  @transient
  private var sc : SparkContext = null

  @Before
  def setup(): Unit = {
    sc = new SparkContext("local", "test", conf)
  }

  @After
  def tearDown(): Unit = {
    sc.stop();
    sc = null;
  }

  @Test
  def testBeans(): Unit = {
    val ints = Range(0, size).map(x => {
      val bean = new SomeBean
      bean.first = 1
      bean.second = 1
      bean
    })
    val t = sc.makeRDD(ints)
      .persist(StorageLevel.MEMORY_ONLY_SER).collect()

    t.foreach(x => Assert.assertTrue(x.first == 1 && x.second == 1))
  }

  @Test
  def testBools() = {
    val bools = Range(0, size).map(x => x % 2 == 0)
    val t = sc.makeRDD(bools).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, bools(x._2))
    })
  }

  @Test
  def testBytes(): Unit = {
    val bytes = Range(0, size).map(x => x.asInstanceOf[Byte])
    val t = sc.makeRDD(bytes).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, bytes(x._2))
    })
  }

  @Test
  def testShorts() = {
    val shorts = Range(0, size).map(x => x.asInstanceOf[Short])
    val t = sc.makeRDD(shorts).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, shorts(x._2))
    })
  }

  @Test
  def testChars() = {
    val chars = Range(0, size).map(x => x.asInstanceOf[Char])
    val t = sc.makeRDD(chars).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, chars(x._2))
    })
  }

  @Test
  def testInts(): Unit = {
    val ints = Range(0, size).map(x => x)
    val t = sc.makeRDD(ints).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, ints(x._2))
    })
  }

  @Test
  def testLongs(): Unit = {
    val longs = Range(0, size).map(x => x.toLong)
    val t = sc.makeRDD(longs).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, longs(x._2))
    })
  }

  @Test
  def testFloats() = {
    val floats = Range(0, size).map(x => x.toFloat)
    val t = sc.makeRDD(floats).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, floats(x._2), 0)
    })
  }

  @Test
  def testDoubles() = {
    val doubles = Range(0, size).map(x => x.toDouble)
    val t = sc.makeRDD(doubles).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, doubles(x._2), 0)
    })
  }

  @Test
  def testStrings() = {
    val strings = Range(0, size).map(x => x.toString)
    val t = sc.makeRDD(strings).persist(StorageLevel.MEMORY_ONLY_SER).collect()
    t.zipWithIndex.foreach(x => {
      Assert.assertEquals(x._1, strings(x._2))
    })
  }
}