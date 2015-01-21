package org.sidney.scala

import java.lang.reflect.{Field, Type}

import com.fasterxml.jackson.databind.`type`.TypeBindings
import org.sidney.core.serde.serializer.{Serializer, SerializerFactory, Serializers}

import scala.reflect.ClassTag

abstract class ScalaSerializerFactory extends SerializerFactory {
  def newScalaSerializer[T](t : Type,
                         field: Field,
                         typeBindings: TypeBindings,
                         serializers: ScalaSerializers)(implicit tag : ClassTag[T]) : Serializer[T]
}