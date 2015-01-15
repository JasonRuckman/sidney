package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BeanSerdeTest extends SerdeTestBase {
    @Test
    public void testPrimitivesBean() {
        AllPrimitives one = getDataFactory().newPrimitives();
        AllPrimitives two = getDataFactory().newPrimitives();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sid sid = new Sid();
        Writer<AllPrimitives> writer = sid.newCachedWriter(AllPrimitives.class, baos);
        writer.write(one);
        writer.write(two);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives> reader = sid.newCachedReader(AllPrimitives.class, bais);
        AllPrimitives out = reader.read();
        AllPrimitives outTwo = reader.read();

        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }

    @Test
    public void testPrimitiveRefsBean() {
        AllPrimitiveRefs one = getDataFactory().newPrimitiveRefs();
        AllPrimitiveRefs two = getDataFactory().newPrimitiveRefs();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitiveRefs> writer = sid.newCachedWriter(AllPrimitiveRefs.class, baos);
        writer.write(one);
        writer.write(two);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitiveRefs> reader = sid.newCachedReader(AllPrimitiveRefs.class, bais);
        AllPrimitiveRefs out = reader.read();
        AllPrimitiveRefs outTwo = reader.read();

        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }

    @Test
    public void testInheritedPrimitivesBean() {
        InheritedAllPrimitives one = getDataFactory().newInheritedAllPrimitives();
        InheritedAllPrimitives two = getDataFactory().newInheritedAllPrimitives();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sid sid = new Sid();
        Writer<InheritedAllPrimitives> writer = sid.newCachedWriter(InheritedAllPrimitives.class, baos);
        writer.write(one);
        writer.write(two);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<InheritedAllPrimitives> reader = sid.newCachedReader(InheritedAllPrimitives.class, bais);
        InheritedAllPrimitives out = reader.read();
        InheritedAllPrimitives outTwo = reader.read();

        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }

    @Test
    public void testInheritedGenerics() {
        GenericsContainer<Integer, Double> one = getDataFactory().newGenericsContainer();
        GenericsContainer<Integer, Double> two = getDataFactory().newGenericsContainer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<GenericsContainer<Integer, Double>> writer = sid.newCachedWriter(
                GenericsContainer.class, baos, Integer.class, Double.class
        );
        writer.write(one);
        writer.write(two);
        writer.close();

        Reader<GenericsContainer<Integer, Double>> reader = sid.newCachedReader(
                GenericsContainer.class, new ByteArrayInputStream(baos.toByteArray()), Integer.class, Double.class
        );

        GenericsContainer<Integer, Double> outOne = reader.read();
        GenericsContainer<Integer, Double> outTwo = reader.read();

        Assert.assertEquals(one, outOne);
        Assert.assertEquals(two, outTwo);
    }

    @Test
    public void testManyPrimitives() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int num = 4097;
        Sid sid = new Sid();
        Writer<AllPrimitives> writer = sid.newCachedWriter(AllPrimitives.class, baos);
        List<AllPrimitives> list = new ArrayList<>();
        List<AllPrimitives> out = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            AllPrimitives record = getDataFactory().newPrimitives();
            writer.write(
                    record
            );
            list.add(record);
        }
        writer.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives> reader = sid.newCachedReader(AllPrimitives.class, bais);
        for (int i = 0; i < num; i++) {
            if (reader.hasNext()) {
                out.add(reader.read());
            }
        }

        AllPrimitives[] expected = list.toArray(new AllPrimitives[list.size()]);
        AllPrimitives[] actual = out.toArray(new AllPrimitives[out.size()]);

        Assert.assertArrayEquals(expected, actual);
    }
}