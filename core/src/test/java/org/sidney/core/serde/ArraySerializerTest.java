package org.sidney.core.serde;

import org.junit.Test;
import org.sidney.core.Sid;
import org.sidney.core.Writer;

import java.io.ByteArrayOutputStream;

public class ArraySerializerTest {
    @Test
    public void test() {
        int[] ints = new int[] { 2, 3 };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<int[]> writer = sid.newCachedWriter(int[].class, baos);
        writer.write(ints);
        writer.write(ints);
        writer.flush();
    }
}