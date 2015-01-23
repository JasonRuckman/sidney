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
package org.sidney.scala

import org.sidney.core.Registrations
import org.sidney.scala.serializers.WrappedArraySerializer

import scala.collection.mutable

class ScalaRegistrations extends Registrations {
  addDefaultScalaTypes()

  def addDefaultScalaTypes(): Unit = {
    register(classOf[mutable.WrappedArray[_]], classOf[WrappedArraySerializer[_]])
  }
}