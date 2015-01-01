package org.sidney.core.serializer;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Reader;
import org.sidney.core.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<List> reader = new Reader<>(List.class, bais);
        List<Integer> results = reader.next();

        Assert.assertEquals(results, ints);
    }
}
