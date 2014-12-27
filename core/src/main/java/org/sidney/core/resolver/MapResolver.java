package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MapResolver extends GenericResolver {
    public MapResolver(Class type, Field field) {
        super(type, field);
    }

    public MapResolver(Class type, Class[] generics) {
        super(type, generics);
    }

    @Override
    public List<Resolver> children() {
        return Arrays.asList(
                new PrimitiveResolver(int.class, null),
                ResolverFactory.resolver(getGenerics()[0]),
                ResolverFactory.resolver(getGenerics()[1])
        );
    }

    @Override
    public void writeRecord(MessageConsumer consumer, Object value, int index) {

    }

    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {

    }
}