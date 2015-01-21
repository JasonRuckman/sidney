package org.sidney.scala

import scala.reflect.ClassTag

object TypeExtractor {
  def createExplicitHashTag[T](value : T) : ClassTag[T] = {
    return new ClassTag[T] {
      override def runtimeClass: Class[_] = value.getClass
    }
  }
}