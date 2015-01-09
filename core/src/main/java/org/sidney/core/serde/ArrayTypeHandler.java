package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArrayTypeHandler extends GenericTypeHandler<ArrayType> {
    private static final Map<Class, ArrayWriters.ArrayWriter> PRIMITIVE_WRITERS = new HashMap<>();

    static {
        PRIMITIVE_WRITERS.put(boolean[].class, new ArrayWriters.BoolArrayWriter());
        PRIMITIVE_WRITERS.put(int[].class, new ArrayWriters.IntArrayWriter());
        PRIMITIVE_WRITERS.put(long[].class, new ArrayWriters.LongArrayWriter());
        PRIMITIVE_WRITERS.put(float[].class, new ArrayWriters.FloatArrayWriter());
        PRIMITIVE_WRITERS.put(double[].class, new ArrayWriters.DoubleArrayWriter());
        PRIMITIVE_WRITERS.put(String[].class, new ArrayWriters.StringArrayWriter());
    }

    private TypeHandler contentTypeHandler;
    private Class rawClass;
    private ArrayWriters.ArrayWriter arrayWriter;

    public ArrayTypeHandler(Type jdkType,
                            Field field,
                            TypeBindings parentTypeBindings,
                            TypeHandlerFactory typeHandlerFactory) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory);

        handlers = new ArrayList<>();
        handlers.add(contentTypeHandler);
        handlers.addAll(contentTypeHandler.getHandlers());

        arrayWriter = PRIMITIVE_WRITERS.get(rawClass);

        if(arrayWriter == null) {
            arrayWriter = new RefArrayWriter();
        }
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
        return null;
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {

    }

    private void writeArray(Object array, TypeWriter typeWriter, WriteContext context) {
        if(typeWriter.writeNullMarker(array, context)) {
            //bump to component column
            context.incrementIndex();
            arrayWriter.writeArray(array, typeWriter, context);
        }
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
    protected void fromType(ArrayType javaType, Type type) {
        rawClass = javaType.getRawClass();
        GenericArrayType genericArrayType = (GenericArrayType) getJdkType();
        JavaType componentJavaType = TypeUtil.type(genericArrayType.getGenericComponentType(), getParentTypeBindings());
        contentTypeHandler = getTypeHandlerFactory().handler(
                componentJavaType.getRawClass(), componentJavaType.getRawClass(), null, getParentTypeBindings()
        );
    }

    @Override
    protected void fromParameterizedClass(ArrayType javaType, Class<?> clazz, Class... types) {
        rawClass = javaType.getRawClass();
        Class<?> componentType = ((Class) getJdkType()).getComponentType();
        contentTypeHandler = getTypeHandlerFactory().handler(componentType, componentType, null, getParentTypeBindings());
    }

    private class RefArrayWriter implements ArrayWriters.ArrayWriter<Object[]> {
        @Override
        public void writeArray(Object[] value, TypeWriter typeWriter, WriteContext context) {
            int index = context.getIndex();
            for (Object o : value) {
                contentTypeHandler.writeValue(o, typeWriter, context);
                context.setIndex(index); //rewind back to start of component type
            }
        }
    }
}