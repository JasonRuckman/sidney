package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.NestedArrayContainer;
import org.sidney.core.NestedBean;
import org.sidney.core.NestedCollection;
import org.sidney.core.NestedGenerics;
import org.sidney.core.serde.BeanTypeHandler;
import org.sidney.core.serde.TypeHandler;
import org.sidney.core.serde.TypeHandlerFactory;

import java.util.List;

public class TypeTest {
    @Test
    public void testNestedGenerics() throws JsonMappingException, NoSuchFieldException {
        BeanTypeHandler beanNode = new BeanTypeHandler(NestedGenerics.class, null, null, TypeHandlerFactory.instance());
        List<TypeHandler> typeHandlers = beanNode.getHandlers();
        Assert.assertEquals(6, typeHandlers.size());
    }

    @Test
    public void testNestedBeans() {
        BeanTypeHandler typeHandler = new BeanTypeHandler(
                NestedBean.class, null, null, TypeHandlerFactory.instance()
        );
        List<TypeHandler> handlers = typeHandler.getHandlers();

        Assert.assertEquals(5, handlers.size());
    }

    @Test
    public void testNestedArrayGenerics() {
        BeanTypeHandler beanNode = new BeanTypeHandler(NestedArrayContainer.class, null, null, TypeHandlerFactory.instance());
        List<TypeHandler> typeHandlers = beanNode.getHandlers();
        Assert.assertEquals(3, typeHandlers.size());
    }

    @Test
    public void testNestedCollectionGenerics() {
        BeanTypeHandler beanTypeHandler = new BeanTypeHandler(
                NestedCollection.class, null, null, TypeHandlerFactory.instance()
        );

        List<TypeHandler> typeHandlers = beanTypeHandler.getHandlers();
        Assert.assertEquals(5, typeHandlers.size());
    }
}
