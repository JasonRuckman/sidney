package org.sidney.core.serializer;

import org.sidney.core.writer.ColumnWriter;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.field.FieldUtils;
import org.sidney.core.field.UnsafeFieldAccessor;
import org.sidney.core.reader.ColumnReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanSerializer<T> extends Serializer {
    private final FieldAccessor[] fields;
    private final List<Serializer> children;
    private final Constructor<T> constructor;
    private int numFields;

    public BeanSerializer(Class<T> type, Field field) {
        super(type, field);

        List<Field> jdkFields = FieldUtils.getAllFields(type);
        fields = new FieldAccessor[jdkFields.size()];
        for (int i = 0; i < jdkFields.size(); i++) {
            fields[i] = new UnsafeFieldAccessor(jdkFields.get(i));
        }

        children = new ArrayList<>();
        for (Field f : FieldUtils.getAllFields(getJdkType())) {
            children.add(SerializerFactory.serializer(f));
        }

        try {
            constructor = type.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        numFields++;
        for(Serializer r : children) {
            numFields += r.numFields();
        }
    }

    @Override
    public List<Serializer> children() {
        return children;
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        if(value == null) {
            consumer.writeNull(index);
            return numFields;
        }

        if(requiresMetaColumn()) {

        }

        consumer.writeNotNull(index++);
        for (int i = 0; i < fields.length; i++) {
            Serializer r = children.get(i);
            index += r.writeRecordFromField(consumer, value, index, fields[i]);
        }
        return numFields;
    }

    @Override
    public int writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        return writeRecord(consumer, accessor.get(parent), index);
    }

    @Override
    public T nextRecord(ColumnReader columnReader, int index) {
        if(!columnReader.readNullMarker(index++)) {
            return null;
        }

        T record = newInstance();
        for(int i = 0; i < fields.length; i++) {
           index += children.get(i).readRecordIntoField(columnReader, record, index, fields[i]);
        }
        return record;
    }

    @Override
    public int readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        if(!columnReader.readNullMarker(index++)) {
            return numFields;
        }

        T record = newInstance();
        for(int i = 0; i < fields.length; i++) {
            index += children.get(i).readRecordIntoField(columnReader, record, index, fields[i]);
        }
        accessor.set(parent, record);
        return numFields;
    }

    @Override
    public int numFields() {
        return fields.length;
    }

    @Override
    public boolean requiresMetaColumn() {
        return getJdkType().isInterface();
    }

    private T newInstance() {
        try {
            return constructor.newInstance();
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}