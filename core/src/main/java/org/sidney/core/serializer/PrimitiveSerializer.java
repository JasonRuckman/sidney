package org.sidney.core.serializer;

import org.sidney.core.annotations.Encode;
import org.sidney.core.writer.ColumnWriter;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.reader.*;
import org.sidney.core.writer.*;
import org.sidney.core.schema.Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimitiveSerializer extends Serializer {
    static final Map<Class, Type> primitiveDefinitions = new HashMap<>();
    static final Map<Class, LeafWriter> writers = new HashMap<>();
    static final Map<Class, LeafReader> readers = new HashMap<>();

    static {
        primitiveDefinitions.put(boolean.class, Type.BOOLEAN);
        primitiveDefinitions.put(Boolean.class, Type.BOOLEAN);
        primitiveDefinitions.put(byte.class, Type.INT32);
        primitiveDefinitions.put(Byte.class, Type.INT32);
        primitiveDefinitions.put(char.class, Type.INT32);
        primitiveDefinitions.put(Character.class, Type.INT32);
        primitiveDefinitions.put(int.class, Type.INT32);
        primitiveDefinitions.put(Integer.class, Type.INT32);
        primitiveDefinitions.put(long.class, Type.INT64);
        primitiveDefinitions.put(Long.class, Type.INT64);
        primitiveDefinitions.put(float.class, Type.FLOAT32);
        primitiveDefinitions.put(Float.class, Type.FLOAT32);
        primitiveDefinitions.put(double.class, Type.FLOAT64);
        primitiveDefinitions.put(Double.class, Type.FLOAT64);
        primitiveDefinitions.put(byte[].class, Type.BINARY);
        primitiveDefinitions.put(String.class, Type.STRING);

        writers.put(boolean.class, new BoolLeafWriter());
        writers.put(int.class, new IntLeafWriter());
        writers.put(float.class, new FloatLeafWriter());
        writers.put(long.class, new LongLeafWriter());
        writers.put(double.class, new DoubleLeafWriter());
        writers.put(String.class, new StringLeafWriter());
        writers.put(byte[].class, new BytesLeafWriter());

        readers.put(boolean.class, new BoolLeafReader());
        readers.put(int.class, new IntLeafReader());
        readers.put(float.class, new FloatLeafReader());
        readers.put(long.class, new LongLeafReader());
        readers.put(double.class, new DoubleLeafReader());
        readers.put(String.class, new StringLeafReader());
        readers.put(byte[].class, new BytesLeafReader());
    }

    private final LeafWriter writer;
    private final LeafReader reader;

    public PrimitiveSerializer(Class type, Field field) {
        super(type, field);

        writer = writers.get(type);
        reader = readers.get(type);
    }

    public static <T> boolean isPrimitive(Class<T> clazz) {
        return primitiveDefinitions.containsKey(clazz);
    }

    @Override
    public Type getType() {
        return primitiveDefinitions.get(getJdkType());
    }

    @Override
    public List<Serializer> children() {
        return new ArrayList<>();
    }

    @Override
    public int writeRecord(ColumnWriter consumer, Object value, int index) {
        throw new IllegalStateException();
    }

    @Override
    public int writeRecordFromField(ColumnWriter consumer, Object parent, int index, FieldAccessor accessor) {
        writer.writeRecordFromField(consumer, parent, index, accessor);
        return 1;
    }

    @Override
    public Object nextRecord(ColumnReader columnReader, int index) {
        throw new IllegalStateException();
    }

    @Override
    public int readRecordIntoField(ColumnReader columnReader, Object parent, int index, FieldAccessor accessor) {
        this.reader.readRecordIntoField(columnReader, parent, index, accessor);
        return 1;
    }

    @Override
    public int numFields() {
        return 1;
    }

    public Encoding getEncoding() {
        if (getField().getAnnotation(Encode.class) != null) {
            return getField().getAnnotation(Encode.class).value();
        }

        return Encoding.PLAIN;
    }

}