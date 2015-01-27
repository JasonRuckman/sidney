package org.sidney.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.serde.serializer.Types;

import java.lang.reflect.*;

public class TypeRefBuilder {
    public static TypeRef typeRef(Type type) {
        return typeRef(type, new Class[0]);
    }

    //build a type tree, don't do any adaptation, simple bean and primitive decomposition
    public static TypeRef typeRef(Type type, Class[] typeParams) {
        return buildTypeRef(type, typeParams, null, null);
    }

    private static TypeRef buildTypeRef(Type type, Class[] typeParams, TypeBindings parentBindings, Field field) {
        JavaType jt;
        TypeBindings typeBindings;
        if (parentBindings == null && typeParams != null && typeParams.length > 0) {
            jt = Types.parameterizedType((Class<?>) type, typeParams);
            typeBindings = Types.binding((Class) type, typeParams);
        } else {
            typeBindings = Types.binding(type, parentBindings);
            jt = Types.type(type, parentBindings);
        }

        TypeRef ref = (field == null) ? new TypeRef(jt.getRawClass()) : new TypeRef.TypeFieldRef(field);
        for (Field subField : Fields.getAllFields(jt.getRawClass())) {
            Type subType = subField.getGenericType();
            TypeRef subRef = buildTypeRef(subType, null, typeBindings, subField);
            ref.addField((TypeRef.TypeFieldRef) subRef);
        }

        if(typeParams == null) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                ParameterizedType t = (ParameterizedType) type;
                for (Type actualTypeArg : t.getActualTypeArguments()) {
                    ref.addTypeParameter(buildTypeRef(actualTypeArg, null, parentBindings, null));
                }
            } else if (TypeVariable.class.isAssignableFrom(type.getClass())) {
                TypeVariable t = (TypeVariable) type;
                for (Type typeBound : t.getBounds()) {
                    ref.addTypeParameter(buildTypeRef(typeBound, null, parentBindings, null));
                }
            } else if (GenericArrayType.class.isAssignableFrom(type.getClass())) {
                GenericArrayType t = (GenericArrayType) type;
                ref.setComponentType(buildTypeRef(t.getGenericComponentType(), null, parentBindings, null));
            }
        } else {
            for(Type typeParam : typeParams) {
                ref.addTypeParameter(buildTypeRef(typeParam, null, typeBindings, null));
            }
        }

        if(jt.isArrayType() && !GenericArrayType.class.isAssignableFrom(type.getClass())) {
            ArrayType arrType = (ArrayType)jt;
            ref.setComponentType(buildTypeRef(arrType.getContentType().getRawClass(), null, null, null));
        }

        return ref;
    }
}