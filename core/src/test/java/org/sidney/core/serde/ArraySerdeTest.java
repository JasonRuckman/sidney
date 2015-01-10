package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ArraySerdeTest extends SerdeTestBase {
    @Test
    public void testInts() {
        int[] ints = getDataFactory().newInts();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<int[]> writer = sid.newCachedWriter(int[].class, baos);
        writer.write(ints);
        writer.write(ints);
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<int[]> reader = sid.newCachedReader(int[].class, bais);
        int[] output = reader.read();

        Assert.assertArrayEquals(ints, output);
    }

    @Test
    public void testArrayOfBeans() {
        AllPrimitives[] primitiveses = new AllPrimitives[] {
                getDataFactory().newPrimitives(),
                getDataFactory().newPrimitives()
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitives[]> writer = sid.newCachedWriter(AllPrimitives[].class, baos);
        writer.write(primitiveses);
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives[]> reader = sid.newCachedReader(AllPrimitives[].class, bais);
        AllPrimitives[] output = reader.read();
        Assert.assertArrayEquals(primitiveses, output);
    }

    @Test
    public void testNestedPrimitiveArrays() {
        AllPrimitiveArrays arrays = getDataFactory().newAllArrays();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitiveArrays> writer = sid.newCachedWriter(AllPrimitiveArrays.class, baos);
        writer.write(arrays);
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitiveArrays> reader = sid.newCachedReader(AllPrimitiveArrays.class, bais);
        AllPrimitiveArrays output = reader.read();
        Assert.assertEquals(arrays, output);
    }

    @Test
    public void testPrimitiveRefArrays() {
        AllPrimitiveRefsArrays one = getDataFactory().newAllPrimitiveRefArrays();
        AllPrimitiveRefsArrays two = getDataFactory().newAllPrimitiveRefArrays();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitiveRefsArrays> writer = sid.newCachedWriter(AllPrimitiveRefsArrays.class, baos);
        writer.write(one);
        writer.write(two);
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitiveRefsArrays> reader = sid.newCachedReader(AllPrimitiveRefsArrays.class, bais);
        AllPrimitiveRefsArrays out = reader.read();
        AllPrimitiveRefsArrays outTwo = reader.read();
        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }
}