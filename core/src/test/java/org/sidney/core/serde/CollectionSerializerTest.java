package org.sidney.core.serde;

import org.junit.Test;
import org.sidney.core.AllPrimitives;
import org.sidney.core.Sid;
import org.sidney.core.Writer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CollectionSerializerTest {
    @Test
    public void testListOfBeans() {
        AllPrimitives one = new AllPrimitives(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        AllPrimitives two = new AllPrimitives(false, 3, 4, 2.5f, 3.7D, new byte[] { (byte) 2 }, "test2");

        List<AllPrimitives> list = new ArrayList<>();
        list.add(one);
        list.add(two);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<List> writer = new Sid().newCachedWriter(List.class, baos, AllPrimitives.class);
        writer.write(list);
        writer.flush();
    }
}