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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.github.jasonruckman.sidney.core.serde.Hint;
import com.github.jasonruckman.sidney.core.type.Types;
import com.github.jasonruckman.sidney.core.type.TypeRef;
import com.github.jasonruckman.sidney.core.util.Fields;

import java.lang.reflect.*;

public class JavaTypeRefBuilder {
  //build a type tree, don't do any adaptation, simple bean and primitive decomposition
  public static TypeRef typeRef(Type type) {
    return buildTypeRef(type, null, null);
  }

  public static TypeRef buildTypeRef(Type type, TypeBindings parentBindings, Field field) {
    JavaType jt;
    TypeBindings typeBindings;

    typeBindings = Types.binding(type, parentBindings);
    jt = Types.type(type, parentBindings);
    TypeRef ref;
    if(field == null) {
      ref = new TypeRef(jt.getRawClass());
    } else {
      Hint hint = field.getAnnotation(Hint.class);
      if(hint != null) {
        ref = new TypeRef.TypeFieldRef(hint.value(), field);
      } else {
        ref = new TypeRef.TypeFieldRef(jt.getRawClass(), field);
      }
    }

    for (Field subField : Fields.getAllFields(jt.getRawClass())) {
      Type subType = subField.getGenericType();
      TypeRef subRef = buildTypeRef(subType, typeBindings, subField);
      ref.addField((TypeRef.TypeFieldRef) subRef);
    }

    if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
      ParameterizedType t = (ParameterizedType) type;
      for (Type actualTypeArg : t.getActualTypeArguments()) {
        ref.addTypeParameter(buildTypeRef(actualTypeArg, parentBindings, null));
      }
    } else if (TypeVariable.class.isAssignableFrom(type.getClass())) {
      TypeVariable t = (TypeVariable) type;
      for (Type typeBound : t.getBounds()) {
        ref.addTypeParameter(buildTypeRef(typeBound, parentBindings, null));
      }
    } else if (GenericArrayType.class.isAssignableFrom(type.getClass())) {
      GenericArrayType t = (GenericArrayType) type;
      ref.addTypeParameter(buildTypeRef(t.getGenericComponentType(), parentBindings, null));
    }

    if (jt.isArrayType() && !GenericArrayType.class.isAssignableFrom(type.getClass())) {
      ArrayType arrType = (ArrayType) jt;
      ref.addTypeParameter(buildTypeRef(arrType.getContentType().getRawClass(), null, null));
    }

    return ref;
  }
}