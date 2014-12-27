package org.sidney.core.resolver;

import org.sidney.core.annotations.Encode;
import org.sidney.core.column.MessageConsumer;
import org.sidney.core.encoding.Encoding;
import org.sidney.core.field.FieldAccessor;
import org.sidney.core.schema.Repetition;
import org.sidney.core.schema.Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimitiveResolver extends Resolver {
    static final Map<Class, Type> primitiveDefinitions = new HashMap<>();
    static final Map<Class, Repetition> primitivesRequired = new HashMap<>();
    static final Map<Class, LeafWriter> writers = new HashMap<>();

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

        primitivesRequired.put(boolean.class, Repetition.REQUIRED);
        primitivesRequired.put(Boolean.class, Repetition.OPTIONAL);
        primitivesRequired.put(byte.class, Repetition.REQUIRED);
        primitivesRequired.put(Byte.class, Repetition.OPTIONAL);
        primitivesRequired.put(char.class, Repetition.REQUIRED);
        primitivesRequired.put(Character.class, Repetition.OPTIONAL);
        primitivesRequired.put(int.class, Repetition.REQUIRED);
        primitivesRequired.put(Integer.class, Repetition.OPTIONAL);
        primitivesRequired.put(long.class, Repetition.REQUIRED);
        primitivesRequired.put(Long.class, Repetition.OPTIONAL);
        primitivesRequired.put(float.class, Repetition.REQUIRED);
        primitivesRequired.put(Float.class, Repetition.OPTIONAL);
        primitivesRequired.put(double.class, Repetition.REQUIRED);
        primitivesRequired.put(Double.class, Repetition.OPTIONAL);
        primitivesRequired.put(byte[].class, Repetition.OPTIONAL);
        primitivesRequired.put(String.class, Repetition.OPTIONAL);

        writers.put(boolean.class, new BoolLeafWriter());
        writers.put(int.class, new IntLeafWriter());
        writers.put(float.class, new FloatLeafWriter());
        writers.put(long.class, new LongLeafWriter());
        writers.put(double.class, new DoubleLeafWriter());
        writers.put(String.class, new StringLeafWriter());
        writers.put(byte[].class, new BytesLeafWriter());
    }

    private final LeafWriter writer;

    public PrimitiveResolver(Class type, Field field) {
        super(type, field);

        writer = writers.get(type);
    }

    public static <T> boolean isPrimitive(Class<T> clazz) {
        return primitiveDefinitions.containsKey(clazz);
    }

    @Override
    public Type getType() {
        return primitiveDefinitions.get(getJdkType());
    }

    @Override
    public List<Resolver> children() {
        return new ArrayList<>();
    }

    @Override
    public void writeRecord(MessageConsumer consumer, Object value, int index) {
        throw new IllegalStateException();
    }

    @Override
    public void writeRecordFromField(MessageConsumer consumer, Object parent, int index, FieldAccessor accessor) {
        writer.writeRecordFromField(consumer, parent, index, accessor);
    }

    public Encoding getEncoding() {
        if (getField().getAnnotation(Encode.class) != null) {
            return getField().getAnnotation(Encode.class).encoding();
        }

        return Encoding.PLAIN;
    }

}