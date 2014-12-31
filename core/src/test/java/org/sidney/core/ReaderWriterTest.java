package org.sidney.core;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ReaderWriterTest {
    @Test
    public void testSimple() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<AllPrimitives> writer = new Writer<>(AllPrimitives.class, baos);
        AllPrimitives test = new AllPrimitives(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        writer.write(test);
        writer.flush();

        InputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives> reader = new Reader<>(AllPrimitives.class, bais);
        AllPrimitives t = reader.next();

        Assert.assertEquals(test, t);
    }

    @Test
    public void testSimpleRefs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<Refs> writer = new Writer<>(Refs.class, baos);
        Refs one = new Refs(false, 1, 2L, 2.0f, 3.0d);
        writer.write(one);
        writer.flush();

        Reader<Refs> reader = new Reader<>(Refs.class, new ByteArrayInputStream(baos.toByteArray()));
        Refs output = reader.next();

        Assert.assertEquals(one, output);
    }

    @Test
    public void testNested() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<NestedRefs> writer = new Writer<>(NestedRefs.class, baos);
        AllPrimitives one = new AllPrimitives(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        AllPrimitives two = new AllPrimitives(false, 3, 4, 2.5f, 3.7D, new byte[] { (byte) 2 }, "test2");
        NestedRefs nestedClass = new NestedRefs(one, two);
        writer.write(nestedClass);
        writer.flush();

        Reader<NestedRefs> reader = new Reader<>(NestedRefs.class, new ByteArrayInputStream(baos.toByteArray()));
        NestedRefs output = reader.next();

        Assert.assertEquals(nestedClass, output);
    }
}