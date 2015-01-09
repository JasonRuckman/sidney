package org.sidney.core.serde;

import com.fasterxml.jackson.databind.type.TypeBindings;
import org.sidney.core.annotations.Encode;
import org.sidney.core.encoding.Encoding;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveTypeHandler extends TypeHandler {
    private static final Map<Class, PrimitiveWriters.PrimitiveWriter> WRITERS = new HashMap<>();
    private static final Map<Class, org.sidney.core.schema.Type> TYPES = new HashMap<>();
    private static final Map<Class, PrimitiveReaders.PrimitiveReader> READERS = new HashMap<>();

    static {
        WRITERS.put(boolean.class, new PrimitiveWriters.BoolWriter());
        WRITERS.put(Boolean.class, new PrimitiveWriters.BoolRefWriter());
        WRITERS.put(int.class, new PrimitiveWriters.IntWriter());
        WRITERS.put(Integer.class, new PrimitiveWriters.IntRefWriter());
        WRITERS.put(long.class, new PrimitiveWriters.LongWriter());
        WRITERS.put(Long.class, new PrimitiveWriters.LongRefWriter());
        WRITERS.put(float.class, new PrimitiveWriters.FloatWriter());
        WRITERS.put(Float.class, new PrimitiveWriters.FloatRefWriter());
        WRITERS.put(double.class, new PrimitiveWriters.DoubleWriter());
        WRITERS.put(Double.class, new PrimitiveWriters.DoubleRefWriter());
        WRITERS.put(byte[].class, new PrimitiveWriters.BytesWriter());
        WRITERS.put(String.class, new PrimitiveWriters.StringWriter());

        READERS.put(boolean.class, new PrimitiveReaders.BoolPrimitiveReader());
        READERS.put(int.class, new PrimitiveReaders.IntPrimitiveReader());
        READERS.put(long.class, new PrimitiveReaders.LongPrimitiveReader());
        READERS.put(float.class, new PrimitiveReaders.FloatPrimitiveReader());
        READERS.put(double.class, new PrimitiveReaders.DoublePrimitiveReader());
        READERS.put(byte[].class, new PrimitiveReaders.BytesPrimitiveReader());
        READERS.put(String.class, new PrimitiveReaders.StringPrimitiveReader());

        TYPES.put(boolean.class, org.sidney.core.schema.Type.BOOLEAN);
        TYPES.put(Boolean.class, org.sidney.core.schema.Type.BOOLEAN);
        TYPES.put(int.class, org.sidney.core.schema.Type.INT32);
        TYPES.put(Integer.class, org.sidney.core.schema.Type.INT32);
        TYPES.put(long.class, org.sidney.core.schema.Type.INT64);
        TYPES.put(Long.class, org.sidney.core.schema.Type.INT64);
        TYPES.put(float.class, org.sidney.core.schema.Type.FLOAT32);
        TYPES.put(Float.class, org.sidney.core.schema.Type.FLOAT32);
        TYPES.put(double.class, org.sidney.core.schema.Type.FLOAT64);
        TYPES.put(Double.class, org.sidney.core.schema.Type.FLOAT64);
        TYPES.put(byte[].class, org.sidney.core.schema.Type.BINARY);
        TYPES.put(String.class, org.sidney.core.schema.Type.STRING);
    }

    private Class<?> actualType;
    private PrimitiveWriters.PrimitiveWriter writer;
    private PrimitiveReaders.PrimitiveReader reader;

    public PrimitiveTypeHandler(Type jdkType, Field field, TypeBindings parentTypeBindings, TypeHandlerFactory typeHandlerFactory) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory);

        actualType = (Class<?>) jdkType;

        writer = WRITERS.get(actualType);
        reader = READERS.get(actualType);
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writer.writeValue(value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writer.writeFromField(parent, typeWriter, context, getAccessor());
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return reader.readValue(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        reader.readIntoField(parent, typeReader, context, getAccessor());
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    @Override
    public String name() {
        return String.format("%s", getActualType().toString());
    }

    public Class<?> getActualType() {
        return actualType;
    }

    public org.sidney.core.schema.Type getType() {
        return TYPES.get(getActualType());
    }

    public Encoding getEncoding() {
        if(getField() != null) {
            return getField().getAnnotation(Encode.class).value();
        }
        return Encoding.PLAIN;
    }

    @Override
    public String toString() {
        return "PrimitiveNode{" +
                "actualType=" + actualType +
                '}';
    }
}