package org.sidney.core.serde;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeBindings;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

public class CollectionTypeHandler<T extends Collection> extends GenericTypeHandler<CollectionType> {
    private TypeHandler contentTypeHandler;
    private Class rawClass;

    public CollectionTypeHandler(Type jdkType,
                                 Field field,
                                 TypeBindings parentTypeBindings,
                                 TypeHandlerFactory typeHandlerFactory,
                                 Class... generics) {
        super(jdkType, field, parentTypeBindings, typeHandlerFactory, generics);

        handlers.add(contentTypeHandler);
        handlers.addAll(contentTypeHandler.getHandlers());
    }

    @Override
    public void writeValue(Object value, TypeWriter typeWriter, WriteContext context) {
        writeCollection((Collection) value, typeWriter, context);
    }

    @Override
    public void writeFromField(Object parent, TypeWriter typeWriter, WriteContext context) {
        writeCollection((Collection) getAccessor().get(parent), typeWriter, context);
    }

    @Override
    public Object readValue(TypeReader typeReader, ReadContext context) {
        return null;
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {

    }

    @Override
    public boolean requiresTypeColumn() {
        return true;
    }

    @Override
    public String name() {
        return String.format("%s", rawClass.toString());
    }

    @Override
    protected void fromParameterizedClass(CollectionType javaType, Class<?> clazz, Class... types) {
        rawClass = javaType.getRawClass();
        contentTypeHandler = getTypeHandlerFactory().handler(
                types[0], types[0], null, getParentTypeBindings()
        );
    }

    @Override
    protected void fromParameterizedType(CollectionType javaType, ParameterizedType type) {
        rawClass = javaType.getRawClass();
        Type[] args = ((ParameterizedType) getJdkType()).getActualTypeArguments();
        contentTypeHandler = getTypeHandlerFactory().handler(
                javaType.getContentType().getRawClass(), args[0], null, getParentTypeBindings()
        );
    }

    @Override
    protected void fromTypeVariable(CollectionType javaType, TypeVariable typeVariable) {
        rawClass = javaType.getRawClass();
        TypeVariable variable = (TypeVariable) getJdkType();
        contentTypeHandler = getTypeHandlerFactory().handler(
                javaType.getRawClass(), variable, null, getParentTypeBindings()
        );
    }

    private void writeCollection(Collection collection, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarkerAndType(collection, context)) {
            context.incrementIndex();
            int index = context.getIndex();
            for (Object value : collection) {
                contentTypeHandler.writeValue(value, typeWriter, context);
                context.setIndex(index); //rewind back to the start of the component type
            }
        }
    }
}