package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.AllPrimitives;
import org.sidney.core.Reader;
import org.sidney.core.Sid;
import org.sidney.core.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CollectionSerdeTest extends SerdeTestBase {
    @Test
    public void testListOfBeans() {
        AllPrimitives one = getDataFactory().newPrimitives();
        AllPrimitives two = getDataFactory().newPrimitives();

        List<AllPrimitives> list = new ArrayList<>();
        list.add(one);
        list.add(two);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<List> writer = sid.newCachedWriter(List.class, baos, AllPrimitives.class);
        writer.write(list);
        writer.close();

        Reader<List<AllPrimitives>> reader = sid.newCachedReader(
                List.class, new ByteArrayInputStream(baos.toByteArray()), AllPrimitives.class
        );

        List<AllPrimitives> output = reader.read();
        Assert.assertEquals(list, output);
    }
}