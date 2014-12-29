/*
package org.sidney.core.resolver;

import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class GenericResolver extends Resolver {
    private Class[] generics;

    public GenericResolver(Class<?> type, Field field) {
        super(type, field);

        resolveGenericsFromField(field);
    }

    public GenericResolver(Class<?> type, Class[] generics) {
        super(type, null);

        this.generics = generics;
    }

    public Class[] getGenerics() {
        return generics;
    }

    private void resolveGenericsFromField(Field field) {
        Type type = field.getGenericType();
        generics = TypeResolver.resolveRawArguments(type, getJdkType());
    }
}
*/
