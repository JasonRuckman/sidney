package org.sidney.core;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class SerializerTest {
    @Test
    public void testSimple() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<TestClass> writer = new Writer<>(TestClass.class, baos);
        TestClass test = new TestClass(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        writer.write(test);
        writer.flush();

        InputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<TestClass> reader = new Reader<>(TestClass.class, bais);
        TestClass t = reader.next();

        Assert.assertEquals(test, t);
    }

    @Test
    public void testSimpleRefs() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<TestRefClass> writer = new Writer<>(TestRefClass.class, baos);
        TestRefClass one = new TestRefClass(false, 1, 2L, 2.0f, 3.0d);
        writer.write(one);
        writer.flush();

        Reader<TestRefClass> reader = new Reader<>(TestRefClass.class, new ByteArrayInputStream(baos.toByteArray()));
        TestRefClass output = reader.next();

        Assert.assertEquals(one, output);
    }

    @Test
    public void testNested() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<TestNestedClass> writer = new Writer<>(TestNestedClass.class, baos);
        TestClass one = new TestClass(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        TestClass two = new TestClass(false, 3, 4, 2.5f, 3.7D, new byte[] { (byte) 2 }, "test2");
        TestNestedClass nestedClass = new TestNestedClass(one, two);
        writer.write(nestedClass);
        writer.flush();

        Reader<TestNestedClass> reader = new Reader<>(TestNestedClass.class, new ByteArrayInputStream(baos.toByteArray()));
        TestNestedClass output = reader.next();

        Assert.assertEquals(nestedClass, output);
    }
}