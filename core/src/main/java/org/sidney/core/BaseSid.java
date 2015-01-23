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

import org.sidney.core.serde.*;
import org.sidney.core.serde.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

public class BaseSid {
    private final Map<Class, Writer> writerCache = new HashMap<>();
    private final Map<Class, Reader> readerCache = new HashMap<>();
    protected boolean caching = false;
    protected Primitives.BoolWriter boolWriter;
    protected Primitives.ByteWriter byteWriter;
    protected Primitives.CharWriter charWriter;
    protected Primitives.ShortWriter shortWriter;
    protected Primitives.IntWriter intWriter;
    protected Primitives.LongWriter longWriter;
    protected Primitives.FloatWriter floatWriter;
    protected Primitives.DoubleWriter doubleWriter;
    protected Primitives.BoolReader boolReader;
    protected Primitives.ByteReader byteReader;
    protected Primitives.CharReader charReader;
    protected Primitives.ShortReader shortReader;
    protected Primitives.IntReader intReader;
    protected Primitives.LongReader longReader;
    protected Primitives.FloatReader floatReader;
    protected Primitives.DoubleReader doubleReader;

    private SidneyConf conf = SidneyConf.newConf();

    public SidneyConf getConf() {
        return conf;
    }

    public Map<Class, Writer> getWriterCache() {
        return writerCache;
    }

    public Map<Class, Reader> getReaderCache() {
        return readerCache;
    }

    public Primitives.BoolWriter newBoolWriter() {
        if (caching) {
            if (boolWriter == null) {
                boolWriter = new Primitives.BoolWriter(conf.getRegistrations());
            }
            return boolWriter;
        }
        return new Primitives.BoolWriter(conf.getRegistrations());
    }

    public Primitives.IntWriter newIntWriter() {
        if (caching) {
            if (intWriter == null) {
                intWriter = new Primitives.IntWriter(conf.getRegistrations());
            }
            return intWriter;
        }

        return new Primitives.IntWriter(conf.getRegistrations());
    }

    public Primitives.IntReader newIntReader() {
        if (caching) {
            if (intReader == null) {
                intReader = new Primitives.IntReader(getConf().getRegistrations());
            }
            return intReader;
        }
        return new Primitives.IntReader(getConf().getRegistrations());
    }

    public void setCaching(boolean caching) {
        this.caching = caching;
    }

    public void addSerializer(Class type, Class<? extends Serializer> serializerType) {
        getConf().getRegistrations().register(
                type, serializerType
        );
    }

    protected <T> Writer<T> createWriter(Class type, Class... generics) {
        if (!caching) {
            return new ObjectWriter<>(type, getConf().getRegistrations(), generics);
        }
        ObjectWriter<T> writer = (ObjectWriter<T>) getWriterCache().get(type);
        if (writer == null) {
            writer = new ObjectWriter<>(type, getConf().getRegistrations(), generics);
            getWriterCache().put(type, writer);
        }
        return writer;
    }

    protected <T> ObjectReader<T> createReader(Class type, Class... generics) {
        if (!caching) {
            return new ObjectReader<>(type, getConf().getRegistrations(), generics);
        }
        ObjectReader<T> reader = (ObjectReader<T>) getReaderCache().get(type);
        if (reader == null) {
            reader = new ObjectReader<>(type, getConf().getRegistrations(), generics);
            getReaderCache().put(type, reader);
        }
        return reader;
    }
}