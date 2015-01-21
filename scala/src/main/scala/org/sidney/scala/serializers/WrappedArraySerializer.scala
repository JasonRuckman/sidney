package org.sidney.scala.serializers

import java.lang.reflect._

import com.fasterxml.jackson.databind.`type`.TypeBindings
import org.sidney.core.{ReflectionFieldAccessor, FieldAccessor}
import org.sidney.core.serde.{WriteContext, TypeWriter, ReadContext, TypeReader}
import org.sidney.core.serde.serializer.{GenericSerializer, Serializers, Serializer}
import org.sidney.scala.{ScalaGenericSerializerFactory, ScalaSerializers}

import scala.collection.mutable
import scala.reflect.ClassTag

class WrappedArraySerializer[T] (jdkType : Type,
                                 field : Field,
                                 parentTypeBindings : TypeBindings,
                                 serializers : ScalaSerializers,
                                 typeParams : scala.Array[Class[_]]) (implicit tag : ClassTag[T])
  extends GenericSerializer[T](jdkType, field, parentTypeBindings, serializers, typeParams) {

  val contentAccessor = new ReflectionFieldAccessor(jdkType.asInstanceOf[Class[_]].getDeclaredField("array"))
  var contentSerializer : Serializer[_] = getHandlers.get(0)

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

  override protected def fromType(t : Type): Unit = {
    val arrClass = java.lang.reflect.Array.newInstance(typeParams(0), 0).getClass
    handlers.add(serializers.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]]))
    handlers.addAll(handlers.get(0).getHandlers)
  }

  override protected def fromArrayType(t : GenericArrayType): Unit = {
    super.fromArrayType(t)
  }

  override protected def fromParameterizedClass(clazz: Class[_], types: Class[_]*): Unit = {
    val arrClass = Array.newInstance(types(0), 0).getClass
    handlers.add(serializers.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]]))
    handlers.addAll(handlers.get(0).getHandlers)
  }

  override protected def fromParameterizedType(t : ParameterizedType): Unit = {
    super.fromParameterizedType(t)
  }

  override protected def fromTypeVariable(typeVariable: TypeVariable[_ <: GenericDeclaration]): Unit = {
    super.fromTypeVariable(typeVariable)
  }
}

class WrappedArraySerializerFactory extends ScalaGenericSerializerFactory {
  override def newScalaSerializer[T](t: Type,
                                  field: Field,
                                  typeBindings: TypeBindings,
                                  serializers: ScalaSerializers)(implicit tag: ClassTag[T]): Serializer[T] = {
    new WrappedArraySerializer[T](t, field, typeBindings, serializers, scala.Array.empty[Class[_]])
  }

  override def newScalaSerializer[T](t: Type,
                                     field: Field,
                                     typeBindings: TypeBindings,
                                     serializers: ScalaSerializers,
                                     typeParams: scala.Array[Class[_]])(implicit tag: ClassTag[T]): Serializer[T] = {
    new WrappedArraySerializer[T](t, field, typeBindings, serializers, typeParams)
  }

  override def newSerializer[T](t : Type,
                                field: Field,
                                typeBindings: TypeBindings,
                                serializers: Serializers): Serializer[T] = {
    ???
  }
}