package org.sidney.core.serializer;

import org.junit.Test;
import org.sidney.core.Writer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CollectionSerializerTest {
    @Test
    public void testListInts() {
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<List> writer = new Writer<>(List.class, baos, Integer.class);
        writer.write(ints);
        writer.flush();
    }
}
