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
        Writer<List<AllPrimitives>> writer = sid.newCachedWriter(List.class, baos, AllPrimitives.class);
        writer.write(list);
        writer.close();

        Reader<List<AllPrimitives>> reader = sid.newCachedReader(
                List.class, new ByteArrayInputStream(baos.toByteArray()), AllPrimitives.class
        );

        List<AllPrimitives> output = reader.read();
        Assert.assertEquals(list, output);
    }

    @Test
    public void testManyListsOfBeans() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<List<AllPrimitives>> writer = sid.newCachedWriter(List.class, baos, AllPrimitives.class);
        List<List<AllPrimitives>> lists = new ArrayList<>();
        int num = 1025;
        for(int i = 0; i < num; i++) {
            List<AllPrimitives> primitiveses = new ArrayList<>();
            for(int j = 0; j < getDataFactory().newByte(); j++) {
                primitiveses.add(
                        getDataFactory().newPrimitives()
                );
            }
            writer.write(primitiveses);
            lists.add(primitiveses);
        }
        writer.close();

        Reader<List<AllPrimitives>> reader = sid.newCachedReader(
                List.class, new ByteArrayInputStream(baos.toByteArray()), AllPrimitives.class
        );
        List<List<AllPrimitives>> out = new ArrayList<>();
        while (reader.hasNext()) {
            out.add(reader.read());
        }
        Assert.assertEquals(lists, out);
    }
}