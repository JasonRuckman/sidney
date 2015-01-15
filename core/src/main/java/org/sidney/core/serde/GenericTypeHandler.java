package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.*;

public abstract class GenericTypeHandler<JT extends JavaType> extends TypeHandler {
    public GenericTypeHandler(Type jdkType,
                              Field field,
                              TypeBindings parentTypeBindings,
                              TypeHandlerFactory typeHandlerFactory, Class... generics) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory, generics);
    }

    protected void fromType(Type type) {
        throw new IllegalStateException();
    }

    protected void fromArrayType(GenericArrayType type) {
        throw new IllegalStateException();
    }

    protected void fromParameterizedClass(Class<?> clazz, Class... types) {
        throw new IllegalStateException();
    }

    protected void fromParameterizedType(ParameterizedType type) {
        throw new IllegalStateException();
    }

    protected void fromTypeVariable(TypeVariable typeVariable) {
        throw new IllegalStateException();
    }

    @Override
    protected void resolveTypes() {
        super.resolveTypes();

        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass())) {
            fromParameterizedType((ParameterizedType) getJdkType());
        } else if (TypeVariable.class.isAssignableFrom(getJdkType().getClass())) {
            TypeVariable variable = (TypeVariable) getJdkType();
            fromTypeVariable(variable);
        } else if (getJdkType().getClass() == Class.class && generics.length > 0) {
            fromParameterizedClass((Class) getJdkType(), generics);
        } else if (GenericArrayType.class.isAssignableFrom(getJdkType().getClass())) {
            fromArrayType((GenericArrayType) getJdkType());
        } else {
            fromType(getJdkType());
        }
    }
}
