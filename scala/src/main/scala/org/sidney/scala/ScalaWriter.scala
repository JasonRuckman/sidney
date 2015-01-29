package org.sidney.scala

import org.sidney.core.Registrations
import org.sidney.core.serde.{BaseWriter, Writer}

import scala.reflect.runtime.universe._

class ScalaWriter[T](registrations : Registrations, tag : TypeTag[T])
  extends BaseWriter[T](registrations, ScalaTypeRefBuilder.typeRef[T]()(tag)) with Writer[T] {
}
