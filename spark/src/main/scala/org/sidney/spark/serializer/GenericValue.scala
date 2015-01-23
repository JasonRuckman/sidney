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

import scala.reflect.runtime.universe._

object GenericValue {
  def apply[T](value: T)(implicit typeTag: TypeTag[T]) = {
    new GenericValue[T](value)
  }

}

/**
 * Container class for encapsulating type information so Sidney can decompose it
 */
class GenericValue[T](val value: T)(implicit val typeTag: TypeTag[T]) {

}