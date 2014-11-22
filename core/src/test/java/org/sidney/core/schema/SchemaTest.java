package org.sidney.core.schema;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.TestArrayClass;
import org.sidney.core.TestClass;
import org.sidney.core.TestMapClass;
import org.sidney.core.resolver.Resolver;
import org.sidney.core.resolver.ResolverFactory;

public class SchemaTest {
    @Test
    public void testSimple() {
        Schema<TestClass> schema = Schema.schema(TestClass.class);

        Assert.assertEquals(2, schema.getFields().size());
    }

    @Test
    public void testArray() {
        Schema<TestArrayClass> schema = Schema.schema(TestArrayClass.class);

        Assert.assertEquals(3, schema.getFields().size());
    }

    @Test
    public void testMap() {
        Schema<TestMapClass> schema = Schema.schema(TestMapClass.class);

        Assert.assertEquals(2, schema.getFields().size());
    }
}
