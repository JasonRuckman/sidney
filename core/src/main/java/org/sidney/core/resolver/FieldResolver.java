package org.sidney.core.resolver;

import org.sidney.core.field.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldResolver extends Resolver {
    public FieldResolver(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    protected List<Resolver> children() {
        List<Resolver> resolvers = new ArrayList<>();
        for(Field f : FieldUtils.getAllFields(getType())) {
            resolvers.add(ResolverFactory.resolver(f));
        }
        return resolvers;
    }
}