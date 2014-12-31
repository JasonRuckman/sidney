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
    static final Map<Class, Type> DEFINITIONS = new HashMap<>();
    static final Map<Class, LeafWriter> WRITERS = new HashMap<>();
    static final Map<Class, LeafReader> READERS = new HashMap<>();

    static {
        DEFINITIONS.put(boolean.class, Type.BOOLEAN);
        DEFINITIONS.put(Boolean.class, Type.BOOLEAN);
        DEFINITIONS.put(byte.class, Type.INT32);
        DEFINITIONS.put(Byte.class, Type.INT32);
        DEFINITIONS.put(char.class, Type.INT32);
        DEFINITIONS.put(Character.class, Type.INT32);
        DEFINITIONS.put(int.class, Type.INT32);
        DEFINITIONS.put(Integer.class, Type.INT32);
        DEFINITIONS.put(long.class, Type.INT64);
        DEFINITIONS.put(Long.class, Type.INT64);
        DEFINITIONS.put(float.class, Type.FLOAT32);
        DEFINITIONS.put(Float.class, Type.FLOAT32);
        DEFINITIONS.put(double.class, Type.FLOAT64);
        DEFINITIONS.put(Double.class, Type.FLOAT64);
        DEFINITIONS.put(byte[].class, Type.BINARY);
        DEFINITIONS.put(String.class, Type.STRING);

        WRITERS.put(boolean.class, new BoolLeafWriter());
        WRITERS.put(Boolean.class, new BoolRefLeafWriter());
        WRITERS.put(int.class, new IntLeafWriter());
        WRITERS.put(Integer.class, new IntRefLeafWriter());
        WRITERS.put(float.class, new FloatLeafWriter());
        WRITERS.put(Float.class, new FloatRefLeafWriter());
        WRITERS.put(long.class, new LongLeafWriter());
        WRITERS.put(Long.class, new LongRefLeafWriter());
        WRITERS.put(double.class, new DoubleLeafWriter());
        WRITERS.put(Double.class, new DoubleRefLeafWriter());
        WRITERS.put(String.class, new StringLeafWriter());
        WRITERS.put(byte[].class, new BytesLeafWriter());

        READERS.put(boolean.class, new BoolLeafReader());
        READERS.put(Boolean.class, new BoolRefLeafReader());
        READERS.put(int.class, new IntLeafReader());
        READERS.put(Integer.class, new IntRefLeafReader());
        READERS.put(float.class, new FloatLeafReader());
        READERS.put(Float.class, new FloatRefLeafReader());
        READERS.put(long.class, new LongLeafReader());
        READERS.put(Long.class, new LongRefLeafReader());
        READERS.put(double.class, new DoubleLeafReader());
        READERS.put(Double.class, new DoubleRefLeafReader());
        READERS.put(String.class, new StringLeafReader());
        READERS.put(byte[].class, new BytesLeafReader());
    }

    private final LeafWriter writer;
    private final LeafReader reader;

    public PrimitiveSerializer(Class type, Field field) {
        super(type, field);

        writer = WRITERS.get(type);
        reader = READERS.get(type);
    }

    public static <T> boolean isPrimitive(Class<T> clazz) {
        return DEFINITIONS.containsKey(clazz);
    }

    @Override
    public Type getType() {
        return DEFINITIONS.get(getJdkType());
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