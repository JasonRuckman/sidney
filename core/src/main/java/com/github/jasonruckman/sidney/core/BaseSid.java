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
package com.github.jasonruckman.sidney.core;

import com.github.jasonruckman.sidney.core.serde.*;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

public class BaseSid {
    private SidneyConf conf = SidneyConf.newConf();

    /**
     * Creates a new writer for writing primitive bools
     */
    public Primitives.BoolWriter newBoolWriter() {
        return new Primitives.BoolWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive bools
     */
    public Primitives.BoolReader newBoolReader() {
        return new Primitives.BoolReader(conf);
    }

    /**
     * Creates a new writer for writing primitive bytes
     */
    public Primitives.ByteWriter newByteWriter() {
        return new Primitives.ByteWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive bytes
     */
    public Primitives.ByteReader newByteReader() {
        return new Primitives.ByteReader(conf);
    }

    /**
     * Creates a new writer for writing primitive chars
     */
    public Primitives.CharWriter newCharWriter() {
        return new Primitives.CharWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive chars
     */
    public Primitives.CharReader newCharReader() {
        return new Primitives.CharReader(conf);
    }

    /**
     * Creates a new writer for writing primitive shorts
     */
    public Primitives.ShortWriter newShortWriter() {
        return new Primitives.ShortWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive shorts
     */
    public Primitives.ShortReader newShortReader() {
        return new Primitives.ShortReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive ints
     */
    public Primitives.IntWriter newIntWriter() {
        return new Primitives.IntWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive ints
     */
    public Primitives.IntReader newIntReader() {
        return new Primitives.IntReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive longs
     */
    public Primitives.LongWriter newLongWriter() {
        return new Primitives.LongWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive longs
     */
    public Primitives.LongReader newLongReader() {
        return new Primitives.LongReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive floats
     */
    public Primitives.FloatWriter newFloatWriter() {
        return new Primitives.FloatWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive floats
     */
    public Primitives.FloatReader newFloatReader() {
        return new Primitives.FloatReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive doubles
     */
    public Primitives.DoubleWriter newDoubleWriter() {
        return new Primitives.DoubleWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive doubles
     */
    public Primitives.DoubleReader newDoubleReader() {
        return new Primitives.DoubleReader(getConf());
    }
    /**
     * Set whether or not to use unsafe field access
     */
    public void useUnsafeFieldAccess(boolean useUnsafe) {
        this.conf.useUnsafe(useUnsafe);
    }

    public void addSerializer(Class type, Class<? extends Serializer> serializerType) {
        getConf().getRegistrations().register(
                type, serializerType
        );
    }

    protected <T> Writer<T> createWriter(TypeToken<T> token) {
        return new JavaWriter<>(getConf(), token);
    }

    protected <T> JavaReader<T> createReader(TypeToken<T> token) {
        return new JavaReader<>(getConf(), token);
    }

    protected SidneyConf getConf() {
        return conf;
    }
}