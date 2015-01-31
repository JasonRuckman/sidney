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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.Registrations;
import org.sidney.core.SidneyException;
import org.sidney.core.TypeRef;
import org.sidney.core.serde.serializer.Serializer;
import org.sidney.core.serde.serializer.SerializerContext;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static org.sidney.core.Bytes.*;

public abstract class BaseWriter<T> {
    public static final int DEFAULT_PAGE_SIZE = 2048;
    protected final ObjectMapper json = new ObjectMapper();
    protected final TypeRef typeRef;
    protected Registrations registrations;
    protected OutputStream outputStream;
    protected int recordCount = 0;
    protected WriteContext context;
    protected SerializerContext builder;
    protected boolean isOpen = false;
    protected Serializer serializer;

    public BaseWriter(Registrations registrations, TypeRef typeRef) {
        this.registrations = registrations;
        this.typeRef = typeRef;
        this.builder = new SerializerContext(registrations);
        ColumnWriter writer = new ColumnWriter();
        this.serializer = builder.serializer(typeRef);
        this.builder.finish(writer);
        this.context = new WriteContextImpl(writer, new PageHeader());
    }

    protected WriteContext getContext() {
        return context;
    }

    /**
     * Write the given value
     */
    public void write(T value) {
        if (!isOpen) {
            throw new SidneyException("Cannot write to a closed writer");
        }
        context.setColumnIndex(0);
        serializer.writeValue(value, context);

        if (++recordCount == DEFAULT_PAGE_SIZE) {
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
        this.context.setPageHeader(new PageHeader());
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
            context.setPageHeader(new PageHeader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writePage(boolean isLastPage) throws IOException {
        writeBoolToStream(isLastPage, outputStream);
        writeIntToStream(recordCount, outputStream);
        writeIntToStream(context.getPageHeader().getClassToValueMap().size(), outputStream);
        for(Map.Entry<Class, Integer> entry : context.getPageHeader().getClassToValueMap().entrySet()) {
            writeStringToStream(entry.getKey().getName(), outputStream);
            writeIntToStream(entry.getValue(), outputStream);
        }
        recordCount = 0;
        context.flushToOutputStream(outputStream);
    }
}