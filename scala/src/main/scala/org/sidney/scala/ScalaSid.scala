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
import org.sidney.core.serde.{Reader, Writer}

import scala.reflect.runtime.universe._

class ScalaSid extends BaseSid {
  def newWriter[T]()(implicit tag: TypeTag[T]): Writer[T] = {
    null
  }

  def newReader[T]()(implicit tag: TypeTag[T]): Reader[T] = {
    null
  }

  def register[T](serializerClass: Class[_ <: Serializer[_]])(implicit tag: TypeTag[T]) = {
    null
  }
}