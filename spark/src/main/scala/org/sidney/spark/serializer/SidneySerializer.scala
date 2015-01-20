package org.sidney.spark.serializer

import java.io._
import java.nio.ByteBuffer

import org.apache.spark.Logging
import org.apache.spark.serializer.{SerializationStream, DeserializationStream, SerializerInstance}
import org.sidney.core.{Bytes, Reader, Writer, Sid}

import scala.reflect.ClassTag

class SidneySerializer extends org.apache.spark.serializer.Serializer
 with Logging
 with Serializable {
  override def newInstance(): SerializerInstance = {
    new SidneySerializerInstance()
  }
}

class SidneySerializerInstance extends SerializerInstance {
  private val sid = new Sid()

  override def serialize[T](t: T)(implicit evidence$1: ClassTag[T]): ByteBuffer = {
    val baos = new ByteArrayOutputStream()
    val writer : Writer[T] = sid.newCachedWriter(evidence$1.runtimeClass)
    writer.write(t)
    writer.close()
    ByteBuffer.wrap(baos.toByteArray)
  }

  override def serializeStream(s: OutputStream): SerializationStream = {
    new SidneySerializationStream(s)
  }

  override def deserializeStream(s: InputStream): DeserializationStream = ???

  override def deserialize[T](bytes: ByteBuffer)(implicit evidence$2: ClassTag[T]): T = {
    val bais = new ByteArrayInputStream(bytes.array())
    val reader : Reader[T] = sid.newCachedReader(evidence$2.runtimeClass)
    if (reader.hasNext) {
      val value = reader.read()
      reader.close()
      return value
    }
    throw new IllegalStateException()
  }

  override def deserialize[T](bytes: ByteBuffer, loader: ClassLoader)(implicit evidence$3: ClassTag[T]): T = {
    deserialize(bytes)
  }
}

class SidneySerializationStream(private val os : OutputStream) extends SerializationStream {
  private var writer : Writer[_] = null
  private val sid = new Sid()

  override def writeObject[T](t: T)(implicit evidence$4: ClassTag[T]): SerializationStream = {
    if(writer == null) {
      writer = sid.newWriter(t.getClass)
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

class SidneyDeserializationStream(private val is : InputStream) extends DeserializationStream {
  private var reader : Reader[_] = null
  private val sid = new Sid()

  override def readObject[T]()(implicit evidence$6: ClassTag[T]): T = {
    if(reader == null) {
      val className = Bytes.readStringFromStream(is)
      val clazz = Class.forName(className)
      reader = sid.newReader(clazz)
      reader.open(is)
    }
    if(!reader.hasNext) {
      throw new EOFException()
    }
    reader.asInstanceOf[Reader[T]].read()
  }

  override def close(): Unit = reader.close()
}