package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
        writer.flush();

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
        writer.flush();

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
        writer.flush();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<InheritedAllPrimitives> reader = sid.newCachedReader(InheritedAllPrimitives.class, bais);
        InheritedAllPrimitives out = reader.read();
        InheritedAllPrimitives outTwo = reader.read();

        Assert.assertEquals(one, out);
        Assert.assertEquals(two, outTwo);
    }
}
