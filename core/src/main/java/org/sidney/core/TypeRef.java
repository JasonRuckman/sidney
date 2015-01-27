package org.sidney.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents type information and type parameters
 */
public class TypeRef {
    private final List<TypeFieldRef> fields = new ArrayList<>();
    private final List<TypeRef> typeParameters = new ArrayList<>();
    private TypeRef componentType;
    private Class<?> type;

    public TypeRef(Class<?> type) {
        this.type = type;
    }

    public TypeRef getComponentType() {
        return componentType;
    }

    public void setComponentType(TypeRef componentType) {
        this.componentType = componentType;
    }

    public List<TypeFieldRef> getFields() {
        return fields;
    }

    public Class<?> getType() {
        return type;
    }

    public List<TypeRef> getTypeParameters() {
        return typeParameters;
    }

    public void addField(TypeFieldRef ref) {
        fields.add(ref);
    }

    public void addTypeParameter(TypeRef ref) {
        typeParameters.add(ref);
    }

    @Override
    public String toString() {
        return "TypeRef{" +
                "fields=" + fields +
                ", typeParameters=" + typeParameters +
                ", componentType=" + componentType +
                ", type=" + type +
                '}';
    }

    public static class TypeFieldRef extends TypeRef {
        private Field jdkField;

        public TypeFieldRef(Field field) {
            super(field.getType());

            this.jdkField = field;
        }

        public Field getJdkField() {
            return jdkField;
        }
    }
}