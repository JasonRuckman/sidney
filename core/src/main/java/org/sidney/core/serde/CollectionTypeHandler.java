package org.sidney.core.serde;

import com.fasterxml.jackson.databind.JavaType;
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
    private InstanceFactoryCache cache = new InstanceFactoryCache();

    public CollectionTypeHandler(Type jdkType,
                                 Field field,
                                 TypeBindings parentTypeBindings,
                                 TypeHandlerFactory typeHandlerFactory, Class... generics) {
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
        return readCollection(typeReader, context);
    }

    @Override
    public void readIntoField(Object parent, TypeReader typeReader, ReadContext context) {
        getAccessor().set(parent, readCollection(typeReader, context));
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
    protected void fromParameterizedClass(Class<?> clazz, Class... types) {
        rawClass = clazz;
        contentTypeHandler = getTypeHandlerFactory().handler(
                types[0], null, getTypeBindings()
        );
    }

    @Override
    protected void fromParameterizedType(ParameterizedType type) {
        Type[] args = ((ParameterizedType) getJdkType()).getActualTypeArguments();
        contentTypeHandler = getTypeHandlerFactory().handler(
                args[0], null, getTypeBindings()
        );
    }

    @Override
    protected void fromTypeVariable(TypeVariable typeVariable) {
        TypeVariable variable = (TypeVariable) getJdkType();
        contentTypeHandler = getTypeHandlerFactory().handler(
                variable, null, getTypeBindings()
        );
    }

    private void writeCollection(Collection collection, TypeWriter typeWriter, WriteContext context) {
        if (typeWriter.writeNullMarkerAndType(collection, context)) {
            context.incrementIndex();
            int index = context.getIndex();
            typeWriter.writeRepetitionCount(context.getIndex(), collection.size(), context);
            for (Object value : collection) {
                contentTypeHandler.writeValue(value, typeWriter, context);
                context.setIndex(index); //rewind back to the start of the component type
            }
        }
    }

    private Object readCollection(TypeReader typeReader, ReadContext context) {
        if(typeReader.readNullMarker(context)) {
            Collection c = (Collection)cache.newInstance(typeReader.readConcreteType(context));
            context.incrementIndex();
            int count = typeReader.readRepetitionCount(context);
            int valueIndex = context.getIndex();
            for(int i = 0; i < count; i++) {
                context.setIndex(valueIndex);
                c.add(contentTypeHandler.readValue(typeReader, context));
            }
            return c;
        }
        return null;
    }
}