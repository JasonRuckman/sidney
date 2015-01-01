package org.sidney.core.serializer;

import org.sidney.core.Container;
import org.sidney.core.Header;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.reader.ColumnReader;
import org.sidney.core.writer.ColumnWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class CollectionSerializer extends Serializer {
    private final Container<Header> header;
    private final Map<Class, Constructor> constructors = new HashMap<>();
    private Class lastClass;
    private Constructor lastConstructor;
    private List<Serializer> children = new ArrayList<>();
    private Serializer componentSerializer;
    private int numFields;

    public CollectionSerializer(Class type, Field field, Container<Header> header) {
        super(type, field);
        this.header = header;

        createChildren(field);
    }

    public CollectionSerializer(Class type, Class componentType, Container<Header> header) {
        super(type, null);
        this.header = header;

        createChildren(componentType);
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
        consumer.writeNotNull(index);
        Collection c = (Collection) value;
        int valueIndex = index + 1;
        consumer.writeConcreteType(c.getClass(), index);
        int count = 0;
        for (Object o : c) {
            componentSerializer.writeRecord(consumer, o, valueIndex);
            ++count;
        }
        consumer.writeRepetitionCount(valueIndex, count);
        return numFields;
    }

    @Override
    public int writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        return writeRecord(consumer, accessor.get(parent), index);
    }

    @Override
    public Object nextRecord(ColumnReader columnReader, int index) {
        if (!columnReader.readNullMarker(index)) {
            return null;
        }

        try {
            Collection c;
            Class concreteType = columnReader.readType(index++);
            if (concreteType == lastClass) {
                c = (Collection) lastConstructor.newInstance();
            } else {
                lastClass = concreteType;
                Constructor constructor = constructors.get(lastClass);
                if (constructor == null) {
                    constructor = lastClass.getConstructor();
                    constructors.put(lastClass, constructor);
                }
                lastConstructor = constructor;
                c = (Collection) lastConstructor.newInstance();
            }
            int numElements = columnReader.readRepetitionCount();
            for (int i = 0; i < numElements; i++) {
                c.add(componentSerializer.nextRecord(columnReader, index));
            }
            return c;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        componentSerializer = SerializerFactory.serializer(field, header);
        //don't worry about nested generics for now
        children.add(componentSerializer);
    }

    private void createChildren(Class componentType) {
        componentSerializer = SerializerFactory.serializerWithoutField(componentType, header);

        children.add(componentSerializer);
    }

    private void calculateSubFields() {
        numFields = 2;
        for (Serializer child : componentSerializer.children()) {
            numFields += child.numFields();
        }
    }
}