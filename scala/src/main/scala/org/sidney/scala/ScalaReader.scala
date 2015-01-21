package org.sidney.scala

import org.sidney.core.serde.serializer.{Serializer, SerializerRepository}
import org.sidney.core.{BaseReader, Registrations}

import scala.reflect.ClassTag

class ScalaReader[T](registrations: Registrations, typeParams : Array[Class[_]])(implicit tag: ClassTag[T])
  extends BaseReader[T](tag.runtimeClass, registrations, typeParams) {
  override protected def getSerializer: Serializer[T] = {
    serializerRepository.asInstanceOf[ScalaSerializerRepository].serializer(`type`, null, null, typeParams)
  }

  override protected def getSerializerWithTypeParams: Serializer[T] = {
    serializerRepository.asInstanceOf[ScalaSerializerRepository].serializer(`type`, null, null, typeParams)
  }

  override protected def getSerializerRepository: SerializerRepository = new ScalaSerializerRepository(registrations)
}