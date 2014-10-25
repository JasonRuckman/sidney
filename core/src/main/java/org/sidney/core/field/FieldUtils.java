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
    private final static Set<Class> primitiveClasses = new HashSet<>();

    static {
        primitiveClasses.add(boolean.class);
        primitiveClasses.add(Boolean.class);
        primitiveClasses.add(byte.class);
        primitiveClasses.add(Byte.class);
        primitiveClasses.add(char.class);
        primitiveClasses.add(Character.class);
        primitiveClasses.add(short.class);
        primitiveClasses.add(Short.class);
        primitiveClasses.add(int.class);
        primitiveClasses.add(Integer.class);
        primitiveClasses.add(float.class);
        primitiveClasses.add(Float.class);
        primitiveClasses.add(long.class);
        primitiveClasses.add(Long.class);
        primitiveClasses.add(double.class);
        primitiveClasses.add(Double.class);
        primitiveClasses.add(byte[].class);
        primitiveClasses.add(String.class);
    }

    /**
     * Returns fields for a {@link Class} and all its super types. Ignores transient or static fields. Fields from the top of the object hierarchy will be added first
     *
     * @param type
     * @return list of valid fields
     */
    public static List<Field> getAllFields(Class type) {
        List<Field> fields = new ArrayList<>();

        if (isConsideredPrimitive(type)) {
            return fields;
        }

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

    /**
     * Tests if a class fits a definition of primitive, defined as it is either a java primitive or its reference variant, a {@link String} a byte[] or an {@link Enum}
     *
     * @param type
     * @return true if the provided type is considered primitive
     */
    public static boolean isConsideredPrimitive(Class type) {
        return primitiveClasses.contains(type) || type.isEnum();
    }
}
