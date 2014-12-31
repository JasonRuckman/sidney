package org.sidney.core.serializer;

import org.junit.Test;
import org.sidney.core.NestedGenerics;
import org.sidney.core.VeryGeneric;

import java.util.ArrayList;
import java.util.List;

public class GenericSerializerTest {
    @Test
    public void testNestedGenericsResolution() {
        List<NestedGenerics> nestedGenericsList = new ArrayList<>();
        CollectionSerializer serializer = new CollectionSerializer(List.class, NestedGenerics.class);

    }
}
