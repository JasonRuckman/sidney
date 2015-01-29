package org.sidney.scala

import org.sidney.core.Registrations
import org.sidney.core.serde.{Reader, BaseReader}

import scala.reflect.runtime.universe._

class ScalaReader[T](registrations : Registrations, tag : TypeTag[T])
  extends BaseReader[T](registrations, ScalaTypeRefBuilder.typeRef[T]()(tag)) with Reader[T]{
}
