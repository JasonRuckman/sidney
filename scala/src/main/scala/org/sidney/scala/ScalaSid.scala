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
package org.sidney.scala

import org.sidney.core._
import org.sidney.core.serde.serializer.Serializer
import org.sidney.core.serde.{ObjectReader, ObjectWriter, Reader, Writer}
import org.sidney.scala.serializers.WrappedArraySerializer

import scala.collection.mutable
import scala.reflect.ClassTag

class ScalaSid extends BaseSid {
  register[mutable.WrappedArray[_]](classOf[WrappedArraySerializer[_]])

  def newWriter[T]()(implicit tag: ClassTag[T]): Writer[T] = {
    new ObjectWriter[T](tag.runtimeClass, getConf.getRegistrations, Array.empty[Class[_]])
  }

  def newWriter[T](typeParams: Class[_]*)(implicit tag: ClassTag[T]): Writer[T] = {
    new ObjectWriter[T](tag.runtimeClass, getConf.getRegistrations, typeParams.toArray)
  }

  def newCachedWriter[T](t: Class[_])(implicit tag: ClassTag[T]): Writer[T] = {
    ???
  }

  def newCachedWriter[T](typeParams: Class[_]*)(implicit tag: ClassTag[T]): Writer[T] = {
    ???
  }

  def newReader[T]()(implicit tag: ClassTag[T]): Reader[T] = {
    new ObjectReader[T](tag.runtimeClass, getConf.getRegistrations, Array.empty)
  }

  def newReader[T](typeParams: Class[_]*)(implicit tag: ClassTag[T]): Reader[T] = {
    new ObjectReader[T](tag.runtimeClass, getConf.getRegistrations, typeParams.toArray)
  }

  def newCachedReader[T]()(implicit tag: ClassTag[T]): Reader[T] = {
    ???
  }

  def newCachedReader[T](typeParams: Class[_]*)(implicit tag: ClassTag[T]): Reader[T] = {
    ???
  }

  def register[T](serializerClass : Class[_ <: Serializer[_]])(implicit tag : ClassTag[T]) = {
    getConf.register(tag.runtimeClass, serializerClass)
  }
}