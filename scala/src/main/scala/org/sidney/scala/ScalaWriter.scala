package org.sidney.scala

import org.sidney.core._
import org.sidney.core.serde.serializer.{Serializer, SerializerRepository}

import scala.reflect.ClassTag

class ScalaWriter[T](registrations: Registrations,
                     typeParams: Array[Class[_]])(implicit val tag: ClassTag[T])
  extends BaseWriter[T](tag.runtimeClass, registrations, typeParams) {
  override protected def getSerializer: Serializer[T] = super.getSerializer

  override protected def getSerializerWithParams: Serializer[T] = serializerRepository.asInstanceOf[ScalaSerializerRepository].serializer(
    `type`, null, null, typeParams
  )

  override protected def getSerializerRepository: SerializerRepository = new ScalaSerializerRepository(registrations)
}