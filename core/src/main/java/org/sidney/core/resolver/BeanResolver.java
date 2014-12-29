package org.sidney.core.resolver;

import org.sidney.core.MessageConsumer;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.field.FieldUtils;
import org.sidney.core.field.UnsafeFieldAccessor;
import org.sidney.core.reader.Reader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanResolver<T> extends Resolver {
    private final FieldAccessor[] fields;
    private final List<Resolver> children;

    public BeanResolver(Class<T> type, Field field) {
        super(type, field);

        List<Field> jdkFields = FieldUtils.getAllFields(type);
        fields = new FieldAccessor[jdkFields.size()];
        for (int i = 0; i < jdkFields.size(); i++) {
            fields[i] = new UnsafeFieldAccessor(jdkFields.get(i));
        }

        children = new ArrayList<>();
        for (Field f : FieldUtils.getAllFields(getJdkType())) {
            children.add(ResolverFactory.resolver(f));
        }
    }

    @Override
    public List<Resolver> children() {
        return children;
    }

    @Override
    public void writeRecord(MessageConsumer consumer, Object value, int index) {
        if(value == null) {
            consumer.writeNull(index);
            return;
        }

        for (int i = 0; i < fields.length; i++) {
            children.get(i).writeRecordFromField(consumer, value, index + i + 1, fields[i]);
        }
    }

    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        writeRecord(consumer, accessor.get(parent), index);
    }

    @Override
    public T nextRecord(Reader reader, int index) {
        T record = reader.newInstance(index);
        for(int i = 0; i < fields.length; i++) {
            children.get(i).readRecordIntoField(reader, record, index + i + 1, fields[i]);
        }
        return record;
    }

    @Override
    public void readRecordIntoField(Reader reader, Object parent, int index, FieldAccessor accessor) {

    }
}