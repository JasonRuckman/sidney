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

class NestedScalaArrays[T >: Null] {
  var nested : T = null

  def canEqual(other: Any): Boolean = other.isInstanceOf[NestedScalaArrays[_]]

  override def equals(other: Any): Boolean = other match {
    case that: NestedScalaArrays[_] =>
      (that canEqual this) &&
        nested == that.nested
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(nested)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}