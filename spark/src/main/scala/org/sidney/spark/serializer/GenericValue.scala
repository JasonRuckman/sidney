package org.sidney.spark.serializer

import scala.reflect.runtime.universe._

object GenericValue {
  def apply[T](value : T)(implicit typeTag : TypeTag[T]) = {
    new GenericValue[T](value)
  }
}

class GenericValue[T](val value : T)(implicit typeTag : TypeTag[T]) {

}