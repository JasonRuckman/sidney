package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.AllPrimitives;
import org.sidney.core.Reader;
import org.sidney.core.Sid;
import org.sidney.core.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BeanSerializerTest {
    @Test
    public void testSimpleBean() {
        AllPrimitives one = new AllPrimitives(false, 1, 2, 1.5f, 2.7D, new byte[] { (byte) 1 }, "test");
        AllPrimitives two = new AllPrimitives(false, 3, 4, 2.5f, 3.7D, new byte[] { (byte) 2 }, "test2");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sid sid = new Sid();
        Writer<AllPrimitives> writer = sid.newCachedWriter(AllPrimitives.class, baos);
        writer.write(one);
        writer.write(two);
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives> reader = sid.newCachedReader(AllPrimitives.class, bais);
        AllPrimitives out = reader.read();
        AllPrimitives outTwo = reader.read();

        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }
}
