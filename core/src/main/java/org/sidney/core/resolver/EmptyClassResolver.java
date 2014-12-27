package org.sidney.core.resolver;

import org.sidney.core.column.MessageConsumer;
import org.sidney.core.field.FieldAccessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * When a class is empty, but still needs to be serialized (jodatime chronologies for example), serialize the class name as a string
 */
public class EmptyClassResolver extends Resolver {
    public EmptyClassResolver(Class type, Field field) {
        super(type, field);
    }

    @Override
    public List<Resolver> children() {
        return new ArrayList<>();
    }

    @Override
    public void writeRecord(MessageConsumer consumer, Object value, int index) {

    }

    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {

    }
}