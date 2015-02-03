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

import java.io.OutputStream;
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

    /**
     * Creates a new writer for writing primitive bools
     */
    public Primitives.BoolWriter newBoolWriter() {
        if (caching) {
            if (boolWriter == null) {
                boolWriter = new Primitives.BoolWriter(conf);
            }
            return boolWriter;
        }
        return new Primitives.BoolWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive bools
     */
    public Primitives.BoolReader newBoolReader() {
        if(caching) {
            if(boolReader == null) {
                boolReader = new Primitives.BoolReader(conf);
            }
            return boolReader;
        }
        return boolReader;
    }

    /**
     * Creates a new writer for writing primitive bytes
     */
    public Primitives.ByteWriter newByteWriter() {
        if(caching) {
            if(byteWriter == null) {
                byteWriter = new Primitives.ByteWriter(conf);
            }
            return byteWriter;
        }
        return new Primitives.ByteWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive bytes
     */
    public Primitives.ByteReader newByteReader() {
        if(caching) {
            if(byteReader == null) {
                byteReader = new Primitives.ByteReader(conf);
            }
            return byteReader;
        }
        return new Primitives.ByteReader(conf);
    }

    /**
     * Creates a new writer for writing primitive chars
     */
    public Primitives.CharWriter newCharWriter() {
        if(caching) {
            if(charWriter == null) {
                charWriter = new Primitives.CharWriter(conf);
            }
            return charWriter;
        }
        return charWriter;
    }

    /**
     * Creates a new reader for reading primitive chars
     */
    public Primitives.CharReader newCharReader() {
        if(caching) {
            if(charReader == null) {
                charReader = new Primitives.CharReader(conf);
            }
            return charReader;
        }
        return charReader;
    }

    /**
     * Creates a new writer for writing primitive shorts
     */
    public Primitives.ShortWriter newShortWriter() {
        if(caching) {
            if(shortWriter == null) {
                shortWriter = new Primitives.ShortWriter(conf);
            }
            return shortWriter;
        }
        return new Primitives.ShortWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive shorts
     */
    public Primitives.ShortReader newShortReader() {
        if(caching) {
            if(shortReader == null) {
                shortReader = new Primitives.ShortReader(conf);
            }
            return shortReader;
        }
        return new Primitives.ShortReader(conf);
    }

    /**
     * Creates a new writer for writing primitive ints
     */
    public Primitives.IntWriter newIntWriter() {
        if (caching) {
            if (intWriter == null) {
                intWriter = new Primitives.IntWriter(conf);
            }
            return intWriter;
        }

        return new Primitives.IntWriter(conf);
    }

    /**
     * Creates a new reader for reading primitive ints
     */
    public Primitives.IntReader newIntReader() {
        if (caching) {
            if (intReader == null) {
                intReader = new Primitives.IntReader(getConf());
            }
            return intReader;
        }
        return new Primitives.IntReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive longs
     */
    public Primitives.LongWriter newLongWriter() {
        if(caching) {
            if(longWriter == null) {
                longWriter = new Primitives.LongWriter(getConf());
            }
            return longWriter;
        }
        return new Primitives.LongWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive longs
     */
    public Primitives.LongReader newLongReader() {
        if(caching) {
            if(longReader == null) {
                longReader = new Primitives.LongReader(getConf());
            }
            return longReader;
        }
        return new Primitives.LongReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive floats
     */
    public Primitives.FloatWriter newFloatWriter() {
        if(caching) {
            if(floatWriter == null) {
                floatWriter = new Primitives.FloatWriter(getConf());
            }
            return floatWriter;
        }
        return new Primitives.FloatWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive floats
     */
    public Primitives.FloatReader newFloatReader() {
        if(caching) {
            if(floatReader == null) {
                floatReader = new Primitives.FloatReader(getConf());
            }
            return floatReader;
        }
        return new Primitives.FloatReader(getConf());
    }

    /**
     * Creates a new writer for writing primitive doubles
     */
    public Primitives.DoubleWriter newDoubleWriter() {
        if(caching) {
            if(doubleWriter == null) {
                doubleWriter = new Primitives.DoubleWriter(getConf());
            }
            return doubleWriter;
        }
        return new Primitives.DoubleWriter(getConf());
    }

    /**
     * Creates a new reader for reading primitive doubles
     */
    public Primitives.DoubleReader newDoubleReader() {
        if(caching) {
            if(doubleReader == null) {
                doubleReader = new Primitives.DoubleReader(getConf());
            }
            return doubleReader;
        }
        return new Primitives.DoubleReader(getConf());
    }
    
    /**
     * Set whether or not to cache readers and writers and reuse when a new writer or reader is requested
     */
    public void setReaderWriterCaching(boolean caching) {
        this.caching = caching;
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

    protected <T> Writer<T> createWriter(Class type, Class... generics) {
        if (!caching) {
            return new JavaWriter<>(type, getConf(), generics);
        }
        JavaWriter<T> writer = (JavaWriter<T>) getWriterCache().get(type);
        if (writer == null) {
            writer = new JavaWriter<>(type, getConf(), generics);
            getWriterCache().put(type, writer);
        }
        return writer;
    }

    protected <T> JavaReader<T> createReader(Class type, Class... generics) {
        if (!caching) {
            return new JavaReader<>(type, getConf(), generics);
        }
        JavaReader<T> reader = (JavaReader<T>) getReaderCache().get(type);
        if (reader == null) {
            reader = new JavaReader<>(type, getConf(), generics);
            getReaderCache().put(type, reader);
        }
        return reader;
    }

    protected Map<Class, Writer> getWriterCache() {
        return writerCache;
    }

    protected Map<Class, Reader> getReaderCache() {
        return readerCache;
    }

    protected SidneyConf getConf() {
        return conf;
    }
}