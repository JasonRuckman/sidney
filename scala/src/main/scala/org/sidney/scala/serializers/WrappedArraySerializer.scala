package org.sidney.scala.serializers

import java.lang.reflect._
import java.util

import com.fasterxml.jackson.databind.`type`.TypeBindings
import org.sidney.core.{ReflectionFieldAccessor, FieldAccessor}
import org.sidney.core.serde.{WriteContext, TypeWriter, ReadContext, TypeReader}
import org.sidney.core.serde.serializer.{GenericSerializer, SerializerRepository, Serializer}
import org.sidney.scala.{ScalaGenericSerializerFactory, ScalaSerializerRepository}

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.collection.JavaConversions._

class WrappedArraySerializer[T] (jdkType : Type,
                                 field : Field,
                                 parentTypeBindings : TypeBindings,
                                 serializers : ScalaSerializerRepository,
                                 typeParams : scala.Array[Class[_]]) (implicit tag : ClassTag[T])
  extends GenericSerializer[T](jdkType, field, parentTypeBindings, serializers, typeParams) {

  val contentAccessor = new ReflectionFieldAccessor(jdkType.asInstanceOf[Class[_]].getDeclaredField("array"))
  var contentSerializer : Serializer[_] = getSerializers.get(0)

  override def writeValue(value: scala.Any, typeWriter: TypeWriter, context: WriteContext) : Unit = {
    if(typeWriter.writeNullMarker(value, context)) {
      context.incrementColumnIndex()
      val content = contentAccessor.get(value);
      contentSerializer.writeValue(content, typeWriter, context)
    } else {
      context.incrementColumnIndex(numSubFields)
    }
  }

  override def writeFromField(parent: scala.Any, typeWriter: TypeWriter, context: WriteContext): Unit = ???

  override def requiresTypeColumn() : Boolean = false

  override def readIntoField(parent: scala.Any, typeReader: TypeReader, context: ReadContext): Unit = ???

  override def readValue(typeReader: TypeReader, context: ReadContext): AnyRef = {
    if(typeReader.readNullMarker(context)) {
      context.incrementColumnIndex()
      val value = contentSerializer.readValue(typeReader, context)
      mutable.WrappedArray.make[T](value)
    } else {
      context.incrementColumnIndex(numSubFields)
      null
    }
  }

  override protected def fromType(t : Type) : util.List[Serializer[_]] = {
    val arrClass = java.lang.reflect.Array.newInstance(typeParams(0), 0).getClass
    subSerializers(serializers.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]]))
  }

  override protected def fromArrayType(t : GenericArrayType) : util.List[Serializer[_]] = {
    super.fromArrayType(t)
  }

  override protected def fromParameterizedClass(clazz: Class[_], types: Class[_]*) : util.List[Serializer[_]] = {
    val arrClass = Array.newInstance(types(0), 0).getClass
    subSerializers(serializers.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]]))
  }

  override protected def fromParameterizedType(t : ParameterizedType) : util.List[Serializer[_]] = {
    super.fromParameterizedType(t)
  }

  override protected def fromTypeVariable(typeVariable: TypeVariable[_ <: GenericDeclaration]) : util.List[Serializer[_]] = {
    super.fromTypeVariable(typeVariable)
  }

  private def subSerializers(contentSerializer : Serializer[_]) : java.util.List[Serializer[_]] = {
    val list = new util.ArrayList[Serializer[_]]()
    list.add(contentSerializer)
    contentSerializer.getSerializers.foreach(x => list.add(x))
    list
  }
}

class WrappedArraySerializerFactory extends ScalaGenericSerializerFactory {
  override def newScalaSerializer[T](t: Type,
                                  field: Field,
                                  typeBindings: TypeBindings,
                                  serializers: ScalaSerializerRepository)(implicit tag: ClassTag[T]): Serializer[T] = {
    new WrappedArraySerializer[T](t, field, typeBindings, serializers, scala.Array.empty[Class[_]])
  }

  override def newScalaSerializer[T](t: Type,
                                     field: Field,
                                     typeBindings: TypeBindings,
                                     serializers: ScalaSerializerRepository,
                                     typeParams: scala.Array[Class[_]])(implicit tag: ClassTag[T]): Serializer[T] = {
    new WrappedArraySerializer[T](t, field, typeBindings, serializers, typeParams)
  }

  override def newSerializer[T](t : Type,
                                field: Field,
                                typeBindings: TypeBindings,
                                serializers: SerializerRepository): Serializer[T] = {
    ???
  }
}