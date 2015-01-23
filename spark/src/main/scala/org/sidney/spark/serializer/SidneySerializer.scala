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

import java.io._
import java.nio.ByteBuffer

import org.apache.spark.serializer._
import org.apache.spark.{Logging, SparkConf}
import org.sidney.core._
import org.sidney.core.serde.Writer
import org.sidney.scala.ScalaSid

import scala.collection.mutable
import scala.reflect.ClassTag

class SidneySerializer(private val sparkConf: SparkConf) extends org.apache.spark.serializer.Serializer
with Logging
with Serializable {
  override def newInstance(): SerializerInstance = {
    new SidneySerializerInstance(sparkConf)
  }
}

class SidneySerializerInstance(conf: SparkConf) extends SerializerInstance {
  private val sid = new ScalaSid()
  //use java serializer for the tasks
  private val javaSerializer = new JavaSerializer(conf)
  private val javaSerializerInstance = javaSerializer.newInstance()

  override def serialize[T](t: T)(implicit evidence$1: ClassTag[T]): ByteBuffer = {
    javaSerializerInstance.serialize(t)
  }

  override def serializeStream(s: OutputStream): SerializationStream = new SidneySerializationStream(s)

  override def deserializeStream(s: InputStream): DeserializationStream = new SidneyDeserializationStream(s)

  override def deserialize[T](bytes: ByteBuffer, loader: ClassLoader)(implicit evidence$3: ClassTag[T]): T = {
    deserialize(bytes)
  }

  override def deserialize[T](bytes: ByteBuffer)(implicit evidence$2: ClassTag[T]): T = {
    javaSerializerInstance.deserialize(bytes)
  }
}

class SidneySerializationStream(private val os: OutputStream) extends SerializationStream {
  private val sid = new ScalaSid()
  private var writer: Writer[_] = null

  override def writeObject[T](t: T)(implicit evidence$4: ClassTag[T]): SerializationStream = {
    createWriterIfNeeded[T](t)
    writer.asInstanceOf[Writer[T]].write(t)
    this
  }

  private def createWriterIfNeeded[T](t: T): Unit = {
    if (writer == null) {
      val tag = ClassTag[T](t.getClass)
      val typeParameters = TypeRegistry.extractTypeParameters(t)

      //reify any type params
      Bytes.writeIntToStream(typeParameters.length, os)
      typeParameters.foreach(x => Bytes.writeStringToStream(x.getName, os))
      writer = sid.newWriter[T](typeParameters: _*)(tag)
      Bytes.writeStringToStream(t.getClass.getName, os)
      writer.open(os)
    }
  }

  override def flush(): Unit = {
    writer.flush()
  }

  override def close(): Unit = {
    writer.close()
    os.close()
  }
}

class SidneyDeserializationStream(private val is: InputStream) extends DeserializationStream {
  private val sid = new ScalaSid()
  private var reader: serde.Reader[_] = null

  override def readObject[T]()(implicit evidence$6: ClassTag[T]): T = {
    createReaderIfNeeded[T]()
    if (!reader.hasNext) {
      throw new java.io.EOFException()
    }
    reader.asInstanceOf[serde.Reader[T]].read()
  }

  private def createReaderIfNeeded[T](): Unit = {
    if (reader == null) {
      val numTypeParams = Bytes.readIntFromStream(is)
      val typeParams = new Array[Class[_]](numTypeParams)
      var counter = 0
      while (counter < numTypeParams) {
        typeParams(counter) = Class.forName(Bytes.readStringFromStream(is))
        counter += 1
      }

      val className = Bytes.readStringFromStream(is)
      val clazz = Class.forName(className)
      val tag = ClassTag[T](clazz)

      reader = sid.newReader(typeParams: _*)(tag)
      reader.open(is)
    }
  }

  override def close(): Unit = {
    reader.close()
    is.close()
  }
}

object TypeRegistry {
  def extractTypeParameters[T](value: T) = {
    value.getClass match {
      case x if classOf[mutable.WrappedArray.ofRef[_]].isAssignableFrom(x) => Array(value.asInstanceOf[mutable.WrappedArray[_]].elemTag.runtimeClass)
      case _ => Array[Class[_]]()
    }
  }
}