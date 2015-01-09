package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.*;

public abstract class GenericTypeHandler<JT extends JavaType> extends TypeHandler {
    public GenericTypeHandler(Type jdkType,
                              Field field,
                              TypeBindings parentTypeBindings,
                              TypeHandlerFactory typeHandlerFactory,
                              Class... generics) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory, generics);
        JT jt = (JT)TypeUtil.type(getJdkType(), getParentTypeBindings());
        if (ParameterizedType.class.isAssignableFrom(getJdkType().getClass())) {
            fromParameterizedType(jt, (ParameterizedType)getJdkType());
        } else if (TypeVariable.class.isAssignableFrom(getJdkType().getClass())) {
            TypeVariable variable = (TypeVariable) getJdkType();
            fromTypeVariable(jt, variable);
        } else if(getJdkType().getClass() == Class.class) {
            fromParameterizedClass(jt, (Class)getJdkType(), generics);
        } else {
            fromType(jt, getJdkType());
        }
    }

    protected void fromType(JT javaType, Type type) {
        throw new IllegalStateException();
    }

    protected void fromParameterizedClass(JT javaType, Class<?> clazz, Class... types) {
        throw new IllegalStateException();
    }

    protected void fromParameterizedType(JT javaType, ParameterizedType type) {
        throw new IllegalStateException();
    }

    protected void fromTypeVariable(JT javaType, TypeVariable typeVariable) {
        throw new IllegalStateException();
    }
}
