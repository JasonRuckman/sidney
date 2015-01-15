package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.Reader;
import org.sidney.core.Sid;
import org.sidney.core.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MapSerdeTest extends SerdeTestBase {
    @Test
    public void testIntToIntMap() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < getRandom().nextInt(128); i++) {
            map.put(getDataFactory().newInt(), getDataFactory().newInt());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<Map<Integer, Integer>> writer = (Writer) sid.newCachedWriter(
                Map.class, baos, Integer.class, Integer.class
        );
        writer.write(map);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<Map<Integer, Integer>> reader = (Reader) sid.newCachedReader(
                Map.class, bais, Integer.class, Integer.class
        );
        Map<Integer, Integer> outMap = reader.read();
        Assert.assertEquals(map, outMap);
    }
}
