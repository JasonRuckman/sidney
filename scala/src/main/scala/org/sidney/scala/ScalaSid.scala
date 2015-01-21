package org.sidney.scala

import java.util

import org.sidney.core._
import org.sidney.core.serde.serializer.SerializerFactory

import scala.reflect.ClassTag

class ScalaSid {
  private val readerCache = new util.HashMap[Class[_], ScalaReader[_]]
  private val writerCache = new util.HashMap[Class[_], ScalaWriter[_]]
  private val conf = SidneyConf.newConf(new ScalaRegistrations)

  def newWriter[T]()(implicit tag : ClassTag[T]) : Writer[T] = {
    new ScalaWriter[T](conf.getRegistrations, Array.empty[Class[_]])
  }

  def newWriter[T](typeParams : Class[_]*)(implicit tag : ClassTag[T]) : Writer[T] = {
    new ScalaWriter[T](conf.getRegistrations, typeParams.toArray)
  }

  def newCachedWriter[T](t : Class[_])(implicit tag : ClassTag[T]): Writer[T] = {
    ???
  }

  def newCachedWriter[T](typeParams : Class[_]*)(implicit tag : ClassTag[T]): Writer[T] = {
    ???
  }

  def newReader[T]()(implicit tag : ClassTag[T]) : Reader[T] = {
    new ScalaReader[T](conf.getRegistrations, Array.empty)
  }

  def newReader[T](typeParams : Class[_]*)(implicit tag : ClassTag[T]) : Reader[T] = {
    new ScalaReader[T](conf.getRegistrations, typeParams.toArray)
  }

  def newCachedReader[T]()(implicit tag : ClassTag[T]) : Reader[T] = {
    ???
  }

  def newCachedReader[T](typeParams : Class[_]*)(implicit tag : ClassTag[T]) : Reader[T] = {
    ???
  }

  def addScalaSerializer[T](factory: ScalaSerializerFactory)(implicit tag : ClassTag[T]) : ScalaSid = {
    conf.getRegistrations.register(tag.runtimeClass, factory)
    this
  }

  def addJavaSerializer(t : Class[_], factory : SerializerFactory) : ScalaSid = {
    ???
  }
}