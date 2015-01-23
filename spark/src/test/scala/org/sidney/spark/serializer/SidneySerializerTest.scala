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
import org.junit.{Assert, Test}

class SidneySerializerTest {
  @Test
  def testBeans(): Unit = {
    val ints = Range(0, 150).map(x => {
      val bean = new SomeBean
      bean.first = 1
      bean.second = 1
      bean
    })
    val conf = new SparkConf()
    conf.set("spark.serializer", classOf[SidneySerializer].getName)
    val sc = new SparkContext("local", "test", conf)
    val t = sc.makeRDD(ints)
      .persist(StorageLevel.MEMORY_ONLY_SER).collect()

    t.foreach(x => Assert.assertTrue(x.first == 1 && x.second == 1))
  }

  @Test
  def testInts(): Unit = {
    val ints = Range(0, 150).map(x => x)
    val conf = new SparkConf()
    conf.set("spark.serializer", classOf[SidneySerializer].getName)
    val sc = new SparkContext("local", "test", conf)
    val t = sc.makeRDD(ints).persist(StorageLevel.MEMORY_ONLY_SER).collect()
  }
}