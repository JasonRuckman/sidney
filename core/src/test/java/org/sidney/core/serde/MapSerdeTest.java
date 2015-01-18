/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.serde;

import org.junit.Assert;
import org.junit.Test;
import org.sidney.core.AllPrimitiveRefs;
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
        Writer<Map<Integer, Integer>> writer = sid.newCachedWriter(
                Map.class, Integer.class, Integer.class
        );
        writer.open(baos);
        writer.write(map);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<Map<Integer, Integer>> reader = sid.newCachedReader(
                Map.class, Integer.class, Integer.class
        );
        reader.open(bais);
        Map<Integer, Integer> outMap = (reader.hasNext()) ? reader.read() : null;
        Assert.assertEquals(map, outMap);
    }

    @Test
    public void testBeanToBeanMap() {
        Map<AllPrimitiveRefs, AllPrimitiveRefs> map = getDataFactory().newMaps();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<Map<AllPrimitiveRefs, AllPrimitiveRefs>> writer = sid.newCachedWriter(
                Map.class, AllPrimitiveRefs.class, AllPrimitiveRefs.class
        );
        writer.open(baos);
        writer.write(map);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<Map<AllPrimitiveRefs, AllPrimitiveRefs>> reader = sid.newCachedReader(
                Map.class, AllPrimitiveRefs.class, AllPrimitiveRefs.class
        );
        reader.open(bais);
        Map<AllPrimitiveRefs, AllPrimitiveRefs> out = (reader.hasNext()) ? reader.read() : null;
        Assert.assertEquals(map, out);
    }
}
