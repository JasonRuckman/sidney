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