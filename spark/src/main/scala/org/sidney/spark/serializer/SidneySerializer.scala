package org.sidney.spark.serializer

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}
import java.nio.ByteBuffer

import org.apache.spark.{SparkConf, Logging}
import org.apache.spark.serializer._
import org.sidney.core._
import org.sidney.scala.{ScalaReader, ScalaSid}

import scala.collection.mutable
import scala.reflect.ClassTag

class SidneySerializer(private val sparkConf: SparkConf) extends org.apache.spark.serializer.Serializer
with Logging
with Serializable {
  override def newInstance(): SerializerInstance = {
    new SidneySerializerInstance(sparkConf)
  }
}

class SidneySerializerInstance(conf : SparkConf) extends SerializerInstance {
  private val sid = new ScalaSid()
  private val kryoInstance = new CustomKryoSerializerInstance(
    new CustomKryoSerializer(conf)
  )
  override def serialize[T](t: T)(implicit evidence$1: ClassTag[T]): ByteBuffer = {
    kryoInstance.serialize(t)
  }

  override def serializeStream(s: OutputStream): SerializationStream = new SidneySerializationStream(s)

  override def deserializeStream(s: InputStream): DeserializationStream = new SidneyDeserializationStream(s)

  override def deserialize[T](bytes: ByteBuffer)(implicit evidence$2: ClassTag[T]): T = {
    kryoInstance.deserialize(bytes)
  }

  override def deserialize[T](bytes: ByteBuffer, loader: ClassLoader)(implicit evidence$3: ClassTag[T]): T = {
    deserialize(bytes)
  }
}

class SidneySerializationStream(private val os: OutputStream) extends SerializationStream {
  private var writer: Writer[_] = null
  private val sid = new ScalaSid()

  override def writeObject[T](t: T)(implicit evidence$4: ClassTag[T]): SerializationStream = {
    if (writer == null) {
      val tag = ClassTag[T](t.getClass)
      val typeParameters = TypeExtractor.extractTypeParameters(t)

      Bytes.writeIntToStream(typeParameters.length, os)
      typeParameters.foreach(x => Bytes.writeStringToStream(x.getName, os))
      writer = sid.newWriter[T](typeParameters:_*)(tag)
      Bytes.writeStringToStream(t.getClass.getName, os)
      writer.open(os)
    }
    writer.asInstanceOf[Writer[T]].write(t)
    this
  }

  override def flush(): Unit = {
    writer.flush()
  }

  override def close(): Unit = {
    writer.close()
  }
}

class SidneyDeserializationStream(private val is: InputStream) extends DeserializationStream {
  private var reader: Reader[_] = null
  private val sid = new ScalaSid()


  override def readObject[T]()(implicit evidence$6: ClassTag[T]): T = {
    if (reader == null) {
      var numTypeParams = Bytes.readIntFromStream(is)
      val typeParams = new Array[Class[_]](numTypeParams)
      var counter = 0
      while (counter < numTypeParams) {
        typeParams(counter) = Class.forName(Bytes.readStringFromStream(is))
        counter += 1
      }

      val className = Bytes.readStringFromStream(is)
      val clazz = Class.forName(className)
      val tag = ClassTag[T](clazz)

      reader = sid.newReader(typeParams:_*)(tag)
      reader.open(is)
    }
    if (!reader.hasNext) {
      throw new java.io.EOFException()
    }
    reader.asInstanceOf[ScalaReader[T]].read()
  }

  override def close(): Unit = reader.close()
}

object TypeExtractor {
  def extractTypeParameters[T](value : T) = {
    value.getClass match {
      case x if classOf[mutable.WrappedArray[_]].isAssignableFrom(x) => Array(value.asInstanceOf[mutable.WrappedArray[_]].elemTag.runtimeClass)
      case _ => Array[Class[_]]()
    }
  }
}