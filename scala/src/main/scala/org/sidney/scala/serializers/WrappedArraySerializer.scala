/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.scala.serializers

import java.lang.reflect._
import java.util

import org.sidney.core.Accessors
import org.sidney.core.Accessors.FieldAccessor
import org.sidney.core.serde.serializer.Serializer
import org.sidney.core.serde.{ReadContext, TypeReader, TypeWriter, WriteContext}

import scala.collection.JavaConversions._
import scala.collection.mutable

class WrappedArraySerializer[T] extends Serializer[T] {
  private val refArrayType = classOf[mutable.WrappedArray.ofRef[_]]
  private var contentAccessor: FieldAccessor = null
  private var contentSerializer: Serializer[_] = null
  private var arrClass: Class[_] = null

  override def writeValue(value: scala.Any, typeWriter: TypeWriter, context: WriteContext): Unit = {
    if (typeWriter.writeNullMarker(value, context)) {
      context.incrementColumnIndex()
      contentSerializer.writeValue(contentAccessor.get(value), typeWriter, context)
    } else {
      context.incrementColumnIndex(getNumFieldsToIncrementBy)
    }
  }

  override def readValue(typeReader: TypeReader, context: ReadContext): AnyRef = {
    if (typeReader.readNullMarker(context)) {
      context.incrementColumnIndex()
      val value = contentSerializer.readValue(typeReader, context)
      mutable.WrappedArray.make[T](value)
    } else {
      context.incrementColumnIndex(getNumFieldsToIncrementBy)
      null
    }
  }

  override def requiresTypeColumn(): Boolean = false

  override def postInit(): Unit = {
    contentAccessor = Accessors.newAccessor(getRawClass.asInstanceOf[Class[_]].getDeclaredField("array"))
  }

  override protected def initFromClass(t: Class[_]): Unit = {
    //is there a better way to figure out an array class from a component type?
    val arrClass = t.asInstanceOf[Class[_]] match {
      case x if x.eq(refArrayType) => java.lang.reflect.Array.newInstance(getTypeParams()(0), 0).getClass
      case x => x.getDeclaredField("array").getType
    }
    contentSerializer = getSerializerRepository.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]])
  }

  override protected def initFromParameterizedClass(clazz: Class[_], types: Class[_]*): Unit = {
    val arrClass = clazz match {
      case x if x.eq(refArrayType) => java.lang.reflect.Array.newInstance(types(0), 0).getClass
      case x => x.getDeclaredField("array").getType
    }
    contentSerializer = getSerializerRepository.serializer(arrClass, null, getTypeBindings, scala.Array.empty[Class[_]])
  }

  override protected def serializersAtThisLevel(): util.List[Serializer[_]] = {
    val list = new util.ArrayList[Serializer[_]]()
    list.add(contentSerializer)
    contentSerializer.getSerializers.foreach(x => list.add(x))
    list
  }
}