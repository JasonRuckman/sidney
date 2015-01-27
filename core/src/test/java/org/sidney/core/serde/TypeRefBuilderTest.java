package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.NestedMap;
import org.sidney.core.TypeRef;
import org.sidney.core.TypeRefBuilder;

public class TypeRefBuilderTest {
    @Test
    public void testNestedMap() {
        TypeRef ref = TypeRefBuilder.typeRef(NestedMap.class, new Class<?>[]{
                Integer.class, Double.class
        });

        Assert.assertEquals(ref.getTypeParameters().size(), 2);
        Assert.assertEquals(ref.getFields().size(), 1);
    }
}