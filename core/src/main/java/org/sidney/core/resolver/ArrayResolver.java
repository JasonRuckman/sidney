package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ArrayResolver extends Resolver {
    private final Resolver lengthResolver;
    private final Resolver componentResolver;

    public ArrayResolver(Class type, Field field) {
        super(type, field);

        Class componentType = getJdkType().getComponentType();

        lengthResolver = ResolverFactory.resolver(int.class);
        componentResolver = ResolverFactory.resolver(componentType);
    }

    @Override
    public List<Resolver> children() {
        return Arrays.asList(lengthResolver, componentResolver);
    }

    @Override
    public void writeRecord(MessageConsumer consumer, Object value, int index) {

    }

    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {

    }
}