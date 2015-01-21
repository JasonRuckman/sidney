package org.sidney.scala

import org.sidney.core.Registrations
import org.sidney.scala.serializers.WrappedArraySerializerFactory

import scala.collection.mutable

class ScalaRegistrations extends Registrations {
  addDefaultScalaTypes()

  def addDefaultScalaTypes() : Unit = {
    register(classOf[mutable.WrappedArray[_]], new WrappedArraySerializerFactory)
  }
}