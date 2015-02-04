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

import com.github.jasonruckman.sidney.core.Bytes;
import com.github.jasonruckman.sidney.core.SidneyConf;
import com.github.jasonruckman.sidney.core.SidneyException;
import com.github.jasonruckman.sidney.core.TypeRef;
import com.github.jasonruckman.sidney.core.serde.serializer.Serializer;
import com.github.jasonruckman.sidney.core.serde.serializer.SerializerContextImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public abstract class BaseWriter<T> {
    private final TypeRef typeRef;
    private OutputStream outputStream;
    private int recordCount = 0;
    private WriteContext writeContext;
    private SerializerContextImpl serializerContext;
    private boolean isOpen = false;
    private Serializer rootSerializer;
    private SidneyConf conf;

    public BaseWriter(SidneyConf conf, TypeRef typeRef) {
        this.conf = conf;
        this.typeRef = typeRef;
        this.serializerContext = new SerializerContextImpl(conf);
        ColumnWriter writer = new ColumnWriter();
        this.rootSerializer = serializerContext.serializer(typeRef);
        this.serializerContext.finish(writer);
        this.writeContext = new WriteContextImpl(writer, new PageHeader(), conf);
    }

    protected WriteContext getWriteContext() {
        return writeContext;
    }

    /**
     * Write the given value
     */
    public void write(T value) {
        if (!isOpen) {
            throw new SidneyException("Cannot write to a closed writer");
        }
        writeContext.setColumnIndex(0);
        rootSerializer.writeValue(value, writeContext);

        if (++recordCount == conf.getPageSize()) {
            flush();
        }
    }

    /**
     * Open this writer against the given {@link java.io.OutputStream}
     */
    public void open(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.recordCount = 0;
        this.isOpen = true;
        this.writeContext.setPageHeader(new PageHeader());
    }

    public void flush() {
        flushPage(false);
    }

    /**
     * Flush the last page and mark the writer as closed
     */
    public void close() {
        flushPage(true);
        isOpen = false;
    }

    public Class<T> getType() {
        return (Class<T>) typeRef.getType();
    }

    private void flushPage(boolean isLastPage) {
        try {
            writePage(isLastPage);
            writeContext.setPageHeader(new PageHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writePage(boolean isLastPage) throws IOException {
        Bytes.writeBoolToStream(isLastPage, outputStream);
        Bytes.writeIntToStream(recordCount, outputStream);
        Bytes.writeIntToStream(writeContext.getPageHeader().getClassToValueMap().size(), outputStream);
        for (Map.Entry<Class, Integer> entry : writeContext.getPageHeader().getClassToValueMap().entrySet()) {
            Bytes.writeStringToStream(entry.getKey().getName(), outputStream);
            Bytes.writeIntToStream(entry.getValue(), outputStream);
        }
        recordCount = 0;
        writeContext.flushToOutputStream(outputStream);
    }
}