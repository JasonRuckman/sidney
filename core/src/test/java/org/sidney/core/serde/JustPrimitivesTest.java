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
import org.sidney.core.Sid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class JustPrimitivesTest {
    @Test
    public void testInts() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Sid sid = new Sid();
        Primitives.IntWriter writer = sid.newIntWriter();
        writer.open(baos);
        int[] ints = new int[100];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
            writer.writeInt(i);
        }
        writer.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Primitives.IntReader reader = sid.newIntReader();
        reader.open(bais);
        int counter = 0;
        while (reader.hasNext()) {
            Assert.assertEquals(ints[counter++], reader.readInt());
        }
    }
}
