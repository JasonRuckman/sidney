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
package com.github.jasonruckman.sidney.scala

import com.github.jasonruckman.sidney.core.serde.serializer.Serializer
import com.github.jasonruckman.sidney.core.serde.{InstanceFactory, Reader, Writer}
import com.github.jasonruckman.sidney.core.{BaseSid, SidneyConf}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

class ScalaSid extends BaseSid {
  private val conf = SidneyConf.newConf()

  /**
   * Create new [[Writer]] from type parameters
   * @param tag type tag for the root type
   * @tparam T root type
   * @return a new writer, bound to the root type
   */
  def newWriter[T]()(implicit tag: TypeTag[T]): Writer[T] = {
    new ScalaWriter[T](conf, tag)
  }

  /**
   * Create new [[Reader]] from type parameters
   * @param tag type tag for the root type
   * @tparam T root type
   * @return a new reader, bound to the root type
   */
  def newReader[T]()(implicit tag: TypeTag[T]): Reader[T] = {
    new ScalaReader[T](conf, tag)
  }

  /**
   * Register new serializer
   * @tparam T target type
   * @tparam R serializer type
   */
  def register[T, R <: Serializer[T]](implicit targetTag: ClassTag[T], serializerTag: ClassTag[R]) = {
    conf.register(
      targetTag.runtimeClass.asInstanceOf[Class[T]], serializerTag.runtimeClass.asInstanceOf[Class[R]]
    )
  }

  def register[T, R <: InstanceFactory[T]](factory : R)(implicit targetTag : ClassTag[T]) = {
    conf.register(targetTag.runtimeClass.asInstanceOf[Class[T]], factory)
  }
}