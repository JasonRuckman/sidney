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
package com.github.jasonruckman.sidney.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents type information and type parameters
 */
public class TypeRef {
  private final List<TypeFieldRef> fields = new ArrayList<>();
  private final List<TypeRef> typeParameters = new ArrayList<>();
  private Class<?> type;

  public TypeRef(Class<?> type) {
    this.type = type;
  }

  /**
   * Get a list of {@link TypeRef} that corresponds to the fields on this class
   *
   * @return the type refs
   */
  public List<TypeFieldRef> getFields() {
    return fields;
  }

  public TypeFieldRef field(String fieldName) {
    for(TypeFieldRef ref : fields) {
      if(ref.getJdkField().getName().equals(fieldName)) {
        return ref;
      }
    }
    return null;
  }

  /**
   * Get the raw class for this {@link TypeRef}
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Set the raw class for this {@link TypeRef}
   */
  public void setType(Class<?> type) {
    this.type = type;
  }

  /**
   * Get the generic type arguments for this {@link TypeRef}
   *
   * @return the type refs
   */
  public List<TypeRef> getTypeParameters() {
    return typeParameters;
  }

  /**
   * Add a {@link TypeRef.TypeFieldRef}
   *
   * @param ref the type ref
   */
  public void addField(TypeFieldRef ref) {
    fields.add(ref);
  }

  /**
   * Add a type parameter {@link TypeRef}
   *
   * @param ref the type ref
   */
  public void addTypeParameter(TypeRef ref) {
    typeParameters.add(ref);
  }

  /**
   * Turn this type ref into a field type ref
   */
  public TypeFieldRef toField(Field field) {
    TypeFieldRef ref = new TypeFieldRef(
        getType(), field
    );

    for (TypeRef typeParam : getTypeParameters()) {
      ref.addTypeParameter(typeParam);
    }

    for (TypeFieldRef fieldRef : getFields()) {
      ref.addField(fieldRef);
    }

    return ref;
  }

  /**
   * Get the type parameter at the given ordinal
   */
  public TypeRef param(int ordinal) {
    return getTypeParameters().get(ordinal);
  }

  public static TypeRef makeRef(Class<?> type) {
    return new TypeRef(type);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypeRef typeRef = (TypeRef) o;

    if (fields != null ? !fields.equals(typeRef.fields) : typeRef.fields != null) return false;
    if (type != null ? !type.equals(typeRef.type) : typeRef.type != null) return false;
    if (typeParameters != null ? !typeParameters.equals(typeRef.typeParameters) : typeRef.typeParameters != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = fields != null ? fields.hashCode() : 0;
    result = 31 * result + (typeParameters != null ? typeParameters.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  /**
   * A {@link TypeRef} associated with a field
   */
  public static class TypeFieldRef extends TypeRef {
    private Field jdkField;

    public TypeFieldRef(Class<?> type) {
      super(type);
    }

    public TypeFieldRef(Class<?> type, Field field) {
      super(type);

      setJdkField(field);
    }

    public Field getJdkField() {
      return jdkField;
    }

    public void setJdkField(Field jdkField) {
      this.jdkField = jdkField;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      if (!super.equals(o)) return false;

      TypeFieldRef that = (TypeFieldRef) o;

      if (jdkField != null ? !jdkField.equals(that.jdkField) : that.jdkField != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (jdkField != null ? jdkField.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "TypeFieldRef{" +
          "jdkField=" + jdkField +
          '}';
    }
  }
}