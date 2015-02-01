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
import org.sidney.core.NestedMap;
import org.sidney.core.TypeRef;
import org.sidney.core.JavaTypeRefBuilder;

public class JavaTypeRefBuilderTest {
    @Test
    public void testNestedMap() {
        TypeRef ref = JavaTypeRefBuilder.typeRef(NestedMap.class, new Class<?>[]{
                Integer.class, Double.class
        });

        Assert.assertEquals(ref.getTypeParameters().size(), 2);
        Assert.assertEquals(ref.getFields().size(), 1);
    }
}