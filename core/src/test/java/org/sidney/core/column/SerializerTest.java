package org.sidney.core.column;

import org.junit.Test;
import org.sidney.core.TestClass;
import org.sidney.core.field.Serializer;

public class SerializerTest {
    @Test
    public void testSimple() {
        Serializer<TestClass> serializer = new Serializer<>(TestClass.class);
        for(int i = 0; i < 100; i++) {
            TestClass record = new TestClass(i, i + 1);
            serializer.serialize(record);
        }
    }
}
