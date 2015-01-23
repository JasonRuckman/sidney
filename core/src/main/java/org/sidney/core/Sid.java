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
package org.sidney.core;

import org.sidney.core.serde.Reader;
import org.sidney.core.serde.Writer;

public class Sid extends BaseSid {
    /**
     * Creates a new {@link org.sidney.core.serde.Writer} for the given type
     *
     * @param type the root type
     * @return a new {@link org.sidney.core.serde.Writer} bound to the given type
     */
    public <T> Writer<T> newWriter(Class type) {
        return createWriter(type);
    }

    /**
     * Creates a new {@link Writer} for the given type with the given type parameters
     *
     * @param type the root type
     * @return a new {@link Writer} bound to the given type
     */
    public <T> Writer<T> newWriter(Class type, Class... generics) {
        return createWriter(type, generics);
    }

    /**
     * Creates a new {@link org.sidney.core.serde.Reader} for the given type
     *
     * @param type the root type
     * @return a new {@link org.sidney.core.serde.Reader} bound to the given type
     */
    public <T> Reader<T> newReader(Class type) {
        return createReader(type);
    }

    /**
     * Creates a new {@link Reader} for the given type and given type parameters
     *
     * @param type the root type
     * @return a new {@link Reader} bound to the given type
     */
    public <T> Reader<T> newReader(Class type, Class... generics) {
        return createReader(type, generics);
    }
}