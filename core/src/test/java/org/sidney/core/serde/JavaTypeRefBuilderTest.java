package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.NestedMap;
import org.sidney.core.TypeRef;
import org.sidney.core.JavaTypeRefBuilder;

public class JavaTypeRefBuilderTest {
    @Test
    public void testNestedMap() {
        TypeRef ref = JavaTypeRefBuilder.typeRef(NestedMap.class, new Class<?>[]{
                Integer.class, Double.class
        });

        Assert.assertEquals(ref.getTypeParameters().size(), 2);
        Assert.assertEquals(ref.getFields().size(), 1);
    }
}