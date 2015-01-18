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

import java.util.HashMap;
import java.util.Map;

public class Sid {
    private final Map<Class, Writer> writerCache = new HashMap<>();
    private final Map<Class, Reader> readerCache = new HashMap<>();
    private SidneyConf conf = new SidneyConf();

    /**
     * Creates a new {@link org.sidney.core.Writer} for the given type
     * @param type the root type
     * @return a new {@link org.sidney.core.Writer} bound to the given type
     */
    public <T> Writer<T> newWriter(Class type) {
        return createWriter(type, false);
    }

    /**
     * Creates a new {@link org.sidney.core.Writer} for the given type with the given type parameters
     * @param type the root type
     * @return a new {@link org.sidney.core.Writer} bound to the given type
     */
    public <T> Writer<T> newWriter(Class type, Class... generics) {
        return createWriter(type, false, generics);
    }

    /**
     * Creates a new {@link org.sidney.core.Writer} for the given type.
     * This writer will be cached and reused if a writer for the same type is requested again
     * @param type the root type
     * @return a new {@link org.sidney.core.Writer} bound to the given type
     */
    public <T> Writer<T> newCachedWriter(Class type) {
        return createWriter(type, true);
    }

    /**
     * Creates a new {@link org.sidney.core.Writer} for the given type with the given type parameters
     * This writer will be cached and reused if a writer for the same type is requested again
     * @param type the root type
     * @return a new {@link org.sidney.core.Writer} bound to the given type
     */
    public <T> Writer<T> newCachedWriter(Class type, Class... generics) {
        return createWriter(type, true, generics);
    }

    /**
     * Creates a new {@link org.sidney.core.Reader} for the given type
     * @param type the root type
     * @return a new {@link org.sidney.core.Reader} bound to the given type
     */
    public <T> Reader<T> newReader(Class type) {
        return createReader(type, false);
    }

    /**
     * Creates a new {@link org.sidney.core.Reader} for the given type and given type parameters
     * @param type the root type
     * @return a new {@link org.sidney.core.Reader} bound to the given type
     */
    public <T> Reader<T> newReader(Class type, Class... generics) {
        return createReader(type, false, generics);
    }

    /**
     * Creates a new {@link org.sidney.core.Reader} for the given type
     * The reader will be cached and reused if a reader for the same type is requested again
     * @param type the root type
     * @return a new {@link org.sidney.core.Reader} bound to the given type
     */
    public <T> Reader<T> newCachedReader(Class type) {
        return createReader(type, true);
    }

    /**
     * Creates a new {@link org.sidney.core.Reader} for the given type with the given type parameters
     * The reader will be cached and reused if a reader for the same type is requested again
     * @param type the root type
     * @return a new {@link org.sidney.core.Reader} bound to the given type
     */
    public <T> Reader<T> newCachedReader(Class type, Class... generics) {
        return createReader(type, true, generics);
    }

    private <T> Writer<T> createWriter(Class type, boolean cache, Class... generics) {
        if (!cache) {
            return new Writer<>(type, conf.getRegistrations(), generics);
        }
        Writer<T> writer = (Writer<T>) writerCache.get(type);
        if (writer == null) {
            writer = new Writer<>(type, conf.getRegistrations(), generics);
            writerCache.put(type, writer);
        }
        return writer;
    }

    private <T> Reader<T> createReader(Class type, boolean cache, Class... generics) {
        if (!cache) {
            return new Reader<>(type, conf.getRegistrations(), generics);
        }
        Reader<T> reader = (Reader<T>) readerCache.get(type);
        if (reader == null) {
            reader = new Reader<>(type, conf.getRegistrations(), generics);
            readerCache.put(type, reader);
        }
        return reader;
    }
}