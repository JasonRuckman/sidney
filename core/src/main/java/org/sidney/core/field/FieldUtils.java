package org.sidney.core.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility methods for resolving classes and fields
 */
public class FieldUtils {
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
}
