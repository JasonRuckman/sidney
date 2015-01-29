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
    private Class<?> type;

    public TypeRef(Class<?> type) {
        this.type = type;
    }

    public List<TypeFieldRef> getFields() {
        return fields;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
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

    public TypeFieldRef toField(Field field) {
        TypeFieldRef ref = new TypeFieldRef(
                getType(), field
        );

        for(TypeRef typeParam : getTypeParameters()) {
            ref.addTypeParameter(typeParam);
        }

        for(TypeFieldRef fieldRef : getFields()) {
            ref.addField(fieldRef);
        }

        return ref;
    }

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
    }
}