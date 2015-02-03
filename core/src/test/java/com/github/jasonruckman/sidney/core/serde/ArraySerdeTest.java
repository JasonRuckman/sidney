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
package com.github.jasonruckman.sidney.core.serde;

import com.github.jasonruckman.sidney.core.AllPrimitiveArrays;
import com.github.jasonruckman.sidney.core.AllPrimitiveRefsArrays;
import com.github.jasonruckman.sidney.core.AllPrimitives;
import com.github.jasonruckman.sidney.core.Sid;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ArraySerdeTest extends SerdeTestBase {
    @Test
    public void testInts() {
        int[] ints = getDataFactory().newInts();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<int[]> writer = sid.newWriter(int[].class);
        writer.open(baos);
        writer.write(ints);
        writer.write(ints);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<int[]> reader = sid.newReader(int[].class);
        reader.open(bais);
        int[] output = (reader.hasNext()) ? reader.read() : null;

        Assert.assertArrayEquals(ints, output);
    }

    @Test
    public void testArrayOfBeans() {
        AllPrimitives[] primitiveses = new AllPrimitives[]{
                getDataFactory().newPrimitives(),
                getDataFactory().newPrimitives()
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitives[]> writer = sid.newWriter(AllPrimitives[].class);
        writer.open(baos);
        writer.write(primitiveses);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitives[]> reader = sid.newReader(AllPrimitives[].class);
        reader.open(bais);
        AllPrimitives[] output = (reader.hasNext()) ? reader.read() : null;
        Assert.assertArrayEquals(primitiveses, output);
    }

    @Test
    public void testNestedPrimitiveArrays() {
        AllPrimitiveArrays arrays = getDataFactory().newAllArrays();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitiveArrays> writer = sid.newWriter(AllPrimitiveArrays.class);
        writer.open(baos);
        writer.write(arrays);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitiveArrays> reader = sid.newReader(AllPrimitiveArrays.class);
        reader.open(bais);
        AllPrimitiveArrays output = (reader.hasNext()) ? reader.read() : null;
        Assert.assertEquals(arrays, output);
    }

    @Test
    public void testPrimitiveRefArrays() {
        AllPrimitiveRefsArrays one = getDataFactory().newAllPrimitiveRefArrays();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Writer<AllPrimitiveRefsArrays> writer = sid.newWriter(AllPrimitiveRefsArrays.class);
        writer.open(baos);
        writer.write(one);
        writer.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Reader<AllPrimitiveRefsArrays> reader = sid.newReader(AllPrimitiveRefsArrays.class);
        reader.open(bais);
        AllPrimitiveRefsArrays out = (reader.hasNext()) ? reader.read() : null;
        Assert.assertEquals(one, out);
    }
}