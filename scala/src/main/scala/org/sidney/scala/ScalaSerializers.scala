package org.sidney.scala

import java.lang.reflect.{Field, Type}

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.`type`.TypeBindings
import org.sidney.core.Registrations
import org.sidney.core.serde.serializer._

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

class ScalaSerializers(registrations : Registrations) extends Serializers(registrations) {
  def serializer[T](t : Type,
                    field: Field,
                    typeBindings: TypeBindings,
                    typeParams : Array[Class[_]])(implicit tag : ClassTag[T]) : Serializer[T] = {
    var jdkType = t
    var javaType: JavaType = null
    if (typeParams.length == 0) {
      javaType = Types.`type`(jdkType, typeBindings)
    }
    else {
      javaType = Types.parameterizedType(jdkType.asInstanceOf[Class[_]], typeParams)
    }

    val clazz: Class[_] = javaType.getRawClass

    val factory = entries.flatMap(x => {
      if(x.getType.eq(clazz) || x.getType.isAssignableFrom(clazz)) {
        Some(x.getSerializerFactory)
      } else {
        None
      }
    }).head

    if (factory.isInstanceOf[PrimitiveSerializer.NonNullPrimitiveSerializerFactory] || factory.isInstanceOf[PrimitiveSerializer.PrimitiveSerializerFactory]) {
      jdkType = clazz
    }

    if(!classOf[ScalaSerializerFactory].isAssignableFrom(factory.getClass)) {
      //regular java serializer
      if (typeParams.length > 0) {
        return factory.asInstanceOf[GenericSerializerFactory].newSerializer[T](
          jdkType, field, typeBindings, this, typeParams
        )
      }

      return factory.newSerializer(
        jdkType, field, typeBindings, this
      )
    }

    if (typeParams.length > 0) {
      factory.asInstanceOf[ScalaGenericSerializerFactory].newScalaSerializer(
        jdkType, field, typeBindings, this, typeParams
      )
    } else {
      factory.asInstanceOf[ScalaSerializerFactory].newScalaSerializer(jdkType, field, typeBindings, this)
    }
  }
}