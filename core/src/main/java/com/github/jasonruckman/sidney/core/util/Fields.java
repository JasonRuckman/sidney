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
package com.github.jasonruckman.sidney.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for resolving classes and fields
 */
public class Fields {
  /**
   * Returns fields for a {@link Class} and all its super types. Ignores transient or static fields. Fields from the top of the object hierarchy will be added first
   *
   * @param type
   * @return list of valid fields
   */
  public static List<Field> getAllFields(Class type) {
    List<Field> fields = new ArrayList<>();

    Field[] declaredFields = type.getDeclaredFields();

    if (type.getSuperclass() != null) {
      fields.addAll(getAllFields(type.getSuperclass()));
    }

    for (Field f : declaredFields) {
      if (Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers())) {
        continue;
      }
      fields.add(f);
    }

    return fields;
  }

  public static List<Field> getAllFieldsNoPrimitiveFilter(Class type) {
    List<Field> fields = new ArrayList<>();

    Field[] declaredFields = type.getDeclaredFields();

    if (type.getSuperclass() != null) {
      fields.addAll(getAllFields(type.getSuperclass()));
    }

    for (Field f : declaredFields) {
      if (Modifier.isStatic(f.getModifiers()) || Modifier.isTransient(f.getModifiers())) {
        continue;
      }
      fields.add(f);
    }

    return fields;
  }

  public static Field getFieldByName(Class containingType, String fieldName) {
    try {
      return containingType.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }
}
