package org.sidney.scala

import java.lang.reflect.{Field, Type}

import com.fasterxml.jackson.databind.`type`.TypeBindings
import org.sidney.core.serde.serializer.{Serializer, Serializers}

import scala.reflect.ClassTag

abstract class ScalaGenericSerializerFactory extends ScalaSerializerFactory {
  def newScalaSerializer[T](t : Type,
                         field: Field,
                         typeBindings: TypeBindings,
                         serializers: ScalaSerializers, typeParams : Array[Class[_]])(implicit tag : ClassTag[T]) : Serializer[T]
}