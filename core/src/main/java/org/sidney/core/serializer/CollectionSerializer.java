package org.sidney.core.serializer;

import org.sidney.core.field.FieldAccessor;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionSerializer extends Serializer {
    private List<Serializer> children = new ArrayList<>();
    private Serializer componentSerializer;
    private Constructor constructor;
    private int numFields;

    public CollectionSerializer(Class type, Field field) {
        super(type, field);

        createChildren(field);
        //createConstructor();
    }

    public CollectionSerializer(Class type, Class componentType) {
        super(type, null);

        createChildren(componentType);
        //createConstructor();
    }

    @Override
    public List<Serializer> children() {
        return children;
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        if (value == null) {
            consumer.writeNull(index);
            return numFields;
        }
        Collection c = (Collection) value;
        int valueIndex = index + 1;
        consumer.writeConcreteType(c.getClass(), index);
        consumer.startRepetition(index);
        for (Object o : c) {
            componentSerializer.writeRecord(consumer, o, valueIndex);
        }
        consumer.endRepetition(index);
        return numFields;
    }

    @Override
    public int writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        return writeRecord(consumer, accessor.get(parent), index);
    }

    @Override
    public Object nextRecord(ColumnReader columnReader, int index) {
        if (!columnReader.readNullMarker(index++)) {
            return null;
        }

        //consider inspecting types so we can use the repetition count to preallocate
        Collection c;
        try {
            c = (Collection) constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int numElements = columnReader.readRepetitionCount();
        for (int i = 0; i < numElements; i++) {
            c.add(componentSerializer.nextRecord(columnReader, index));
        }
        return c;
    }

    @Override
    public int readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        accessor.set(parent, nextRecord(columnReader, index));
        return numFields;
    }

    @Override
    public int numFields() {
        return numFields;
    }

    @Override
    public boolean requiresMetaColumn() {
        return true;
    }

    private void createChildren(Field field) {
        componentSerializer = SerializerFactory.serializer(field);
        //don't worry about nested generics for now
        children.add(SerializerFactory.serializer(int.class));
        children.add(componentSerializer);
    }

    private void createChildren(Class componentType) {
        componentSerializer = SerializerFactory.serializer(componentType);
        children.add(SerializerFactory.serializer(int.class));
        children.add(componentSerializer);
    }

    private void calculateSubFields() {
        numFields = 2;
        for (Serializer child : componentSerializer.children()) {
            numFields += child.numFields();
        }
    }
}