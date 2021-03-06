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
package com.github.jasonruckman.sidney.core.serde.serializer;

import com.github.jasonruckman.sidney.core.util.Accessors;
import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.serde.Contexts;
import com.github.jasonruckman.sidney.core.serde.factory.Factories;

/**
 * Handles serializing a given type, is responsible for decomposing that type and constructing sub serializers if necessary
 */
public abstract class Serializer<T> {
  private int startIndex;
  private Factories factories;

  private Accessors.FieldAccessor accessor;

  public Serializer() {

  }

  /**
   * Called after a type ref is fully constructed, the implementing class should use this information to create any sub-serializers necessary
   */
  public abstract void initialize(TypeRef typeRef, SerializerContext context);

  /**
   * Fully consume a value
   * The {@link com.github.jasonruckman.sidney.core.serde.Contexts.WriteContext#getColumnIndex()} on context must be incremented to the number of fields + subfields + 1 after the value is consumed
   * For example, if value is a bean with two int fields, it must be incremented by 4, one for the bean, two for the ints,
   * and one more to advance into the next field
   */
  public abstract void writeValue(T value, Contexts.WriteContext context);

  /**
   * Fully consume a field value from the parent, parent is guaranteed to be non-null
   * Follow the same incrementing rules as {@link #writeValue}
   */
  public void writeFromField(Object parent, Contexts.WriteContext context) {
    writeValue((T) getAccessor().get(parent), context);
  }

  /**
   * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
   * in either {@link #writeValue} or {@link #writeFromField}
   *
   * @return a fully materialized value
   */
  public abstract T readValue(Contexts.ReadContext context);

  /**
   * Materialize a value from sub columns, columns must be incremented and read in the same order as they were written
   * in either {@link #writeValue} or {@link #writeFromField} and the materialized value must be written into the field of the parent
   */
  public void readIntoField(Object parent, Contexts.ReadContext context) {
    getAccessor().set(parent, readValue(context));
  }

  /**
   * Denotes whether or not the value requires serializing the type e.g {@link java.util.Map} so that a reader knows what constructor to use
   *
   * @return whether or not a type column is required
   */
  public boolean requiresTypeColumn() {
    return false;
  }

  /**
   * Get the accessor for this field, if present, otherwise null
   *
   * @return the accessor
   */
  protected Accessors.FieldAccessor getAccessor() {
    return accessor;
  }

  public void setAccessor(Accessors.FieldAccessor accessor) {
    this.accessor = accessor;
  }

  /**
   * Get the index where this serializer starts
   */
  public int startIndex() {
    return startIndex;
  }

  void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  protected Factories getFactories() {
    return factories;
  }

  void setFactories(Factories factories) {
    this.factories = factories;
  }
}