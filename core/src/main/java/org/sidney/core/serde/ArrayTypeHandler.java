package org.sidney.core.serde;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArrayTypeHandler extends GenericTypeHandler<ArrayType> {
    private static final Map<Class, Arrays.ArrayWriters.ArrayWriter> PRIMITIVE_WRITERS = new HashMap<>();
    private static final Map<Class, Arrays.ArrayReaders.ArrayReader> PRIMITIVE_READERS = new HashMap<>();

    static {
        PRIMITIVE_WRITERS.put(boolean[].class, new Arrays.ArrayWriters.BoolArrayWriter());
        PRIMITIVE_WRITERS.put(char[].class, new Arrays.ArrayWriters.CharArrayWriter());
        PRIMITIVE_WRITERS.put(short[].class, new Arrays.ArrayWriters.ShortArrayWriter());
        PRIMITIVE_WRITERS.put(int[].class, new Arrays.ArrayWriters.IntArrayWriter());
        PRIMITIVE_WRITERS.put(long[].class, new Arrays.ArrayWriters.LongArrayWriter());
        PRIMITIVE_WRITERS.put(float[].class, new Arrays.ArrayWriters.FloatArrayWriter());
        PRIMITIVE_WRITERS.put(double[].class, new Arrays.ArrayWriters.DoubleArrayWriter());

        PRIMITIVE_READERS.put(boolean[].class, new Arrays.ArrayReaders.BoolArrayReader());
        PRIMITIVE_READERS.put(char[].class, new Arrays.ArrayReaders.CharArrayReader());
        PRIMITIVE_READERS.put(short[].class, new Arrays.ArrayReaders.ShortArrayReader());
        PRIMITIVE_READERS.put(int[].class, new Arrays.ArrayReaders.IntArrayReader());
        PRIMITIVE_READERS.put(long[].class, new Arrays.ArrayReaders.LongArrayReader());
        PRIMITIVE_READERS.put(float[].class, new Arrays.ArrayReaders.FloatArrayReader());
        PRIMITIVE_READERS.put(double[].class, new Arrays.ArrayReaders.DoubleArrayReader());
    }

    private TypeHandler contentTypeHandler;
    private Class rawClass;
    private Arrays.ArrayWriters.ArrayWriter arrayWriter;
    private Arrays.ArrayReaders.ArrayReader arrayReader;

    public ArrayTypeHandler(Type jdkType,
                            Field field,
                            TypeBindings parentTypeBindings,
                            TypeHandlerFactory typeHandlerFactory, Class... generics) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory, generics);

        handlers = new ArrayList<>();
        handlers.add(contentTypeHandler);
        handlers.addAll(contentTypeHandler.getHandlers());

        arrayWriter = PRIMITIVE_WRITERS.get(rawClass);
        if (arrayWriter == null) {
            arrayWriter = new RefArrayWriter();
        }
        arrayReader = PRIMITIVE_READERS.get(rawClass);
        if (arrayReader == null) {
            arrayReader = new RefArrayReader();
        }
        numSubFields += contentTypeHandler.numSubFields;
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeArray(value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writeArray(getAccessor().get(parent), typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return readArray(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readValue(typeReader, context));
    }

    @Override
    public boolean requiresTypeColumn() {
        return false;
    }

    @Override
    public String name() {
        return String.format("%s", rawClass);
    }

    @Override
    protected void fromType(Type type) {
        rawClass = (Class) type;
        contentTypeHandler = getTypeHandlerFactory().handler(
                rawClass.getComponentType(),
                null, getParentTypeBindings()
        );
    }

    @Override
    protected void fromArrayType(GenericArrayType type) {
        rawClass = TypeUtil.type(type, getParentTypeBindings()).getRawClass();
        GenericArrayType genericArrayType = (GenericArrayType) getJdkType();
        contentTypeHandler = getTypeHandlerFactory().handler(
                genericArrayType.getGenericComponentType(), null, getParentTypeBindings()
        );
    }

    @Override
    protected void fromParameterizedClass(Class<?> clazz, Class... types) {
        rawClass = clazz;
        Class<?> componentType = ((Class) getJdkType()).getComponentType();
        contentTypeHandler = getTypeHandlerFactory().handler(componentType, null, getTypeBindings());
    }

    private void writeArray(Object array, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarker(array, context)) {
            typeWriter.writeRepetitionCount(context.getColumnIndex(), Array.getLength(array), context);
            //bump to component column
            context.incrementColumnIndex();
            arrayWriter.writeArray(array, typeWriter, context);
            context.incrementColumnIndex();
        } else {
            context.incrementColumnIndex(numSubFields);
        }
    }

    private Object readArray(TypeReader typeReader, ReadContext context) {
        if (typeReader.readNullMarker(context)) {
            int arraySize = typeReader.readRepetitionCount(context);
            Object array = Array.newInstance(rawClass.getComponentType(), arraySize);
            context.incrementColumnIndex();
            arrayReader.readValue(typeReader, context, array);
            context.incrementColumnIndex();
            return array;
        }
        context.incrementColumnIndex(numSubFields);
        return null;
    }

    private class RefArrayWriter implements Arrays.ArrayWriters.ArrayWriter<Object[]> {
        @Override
        public void writeArray(Object[] value, TypeWriter typeWriter, WriteContext context) {
            int index = context.getColumnIndex();
            for (Object o : value) {
                contentTypeHandler.writeValue(o, typeWriter, context);
                context.setColumnIndex(index); //rewind back to start of component type
            }
        }
    }

    private class RefArrayReader implements Arrays.ArrayReaders.ArrayReader<Object[]> {
        @Override
        public void readValue(TypeReader typeReader, ReadContext context, Object[] newArray) {
            int index = context.getColumnIndex();
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = contentTypeHandler.readValue(typeReader, context);
                context.setColumnIndex(index);
            }
        }
    }
}