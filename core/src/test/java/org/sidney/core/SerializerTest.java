package org.sidney.core;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class SerializerTest {
    @Test
    public void testSimple() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<TestClass> serializer = new Writer<>(TestClass.class, baos);
        TestClass test = new TestClass(1, 2);
        serializer.write(test);
        serializer.flush();

        InputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<TestClass> reader = new Reader<>(TestClass.class, bais);
        TestClass t = reader.next();

        Assert.assertEquals(test, t);
    }

    @Test
    public void testNested() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer<TestNestedClass> serializer = new Writer<>(TestNestedClass.class, baos);
        TestClass one = new TestClass(1, 2);
        TestClass two = new TestClass(3, 4);
        TestNestedClass nestedClass = new TestNestedClass(one, two);
        serializer.write(nestedClass);
        serializer.flush();

        Reader<TestNestedClass> reader = new Reader<>(TestNestedClass.class, new ByteArrayInputStream(baos.toByteArray()));
        TestNestedClass output = reader.next();

        Assert.assertEquals(nestedClass, output);
    }
}
