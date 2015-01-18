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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sidney.core.exception.SidneyException;
import org.sidney.core.serde.*;
import org.sidney.core.serde.handler.TypeHandler;
import org.sidney.core.serde.handler.TypeHandlerFactory;

import java.io.IOException;
import java.io.OutputStream;

import static org.sidney.core.Bytes.*;

/**
 * Writers values to a given {@link java.io.OutputStream}
 */
public class Writer<T> {
    public static final int DEFAULT_PAGE_SIZE = 1024;

    private final Class<T> type;
    private final TypeHandler handler;
    private final ObjectMapper json = new ObjectMapper();
    private final TypeWriter typeWriter;
    private OutputStream outputStream;
    private int recordCount = 0;
    private Class[] generics = null;
    private WriteContext context;
    private TypeHandlerFactory handlerFactory;
    private boolean isOpen = false;

    //don't use a type variable since it screws up the inference
    Writer(Class type, Registrations registrations) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.handler = handlerFactory.handler(type, null, null);
        this.context = new WriteContext(new ColumnWriter(handler), new PageHeader());
        this.typeWriter = new TypeWriter();
    }

    Writer(Class type, Registrations registrations, Class... generics) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.generics = generics;
        this.handler = handlerFactory.handler(type, null, null, generics);
        this.context = new WriteContext(new ColumnWriter(handler), new PageHeader());
        this.typeWriter = new TypeWriter();
    }

    /**
     * Get the root type of this writer
     * @return the root type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Write the given value
     */
    public void write(T value) {
        if(!isOpen) {
            throw new SidneyException("Cannot write to a closed writer");
        }
        context.setColumnIndex(0);
        handler.writeValue(value, typeWriter, context);

        if (++recordCount == DEFAULT_PAGE_SIZE) {
            flushPage(false);
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

    /**
     * Flush the last page and mark the writer as closed
     */
    public void close() {
        flushPage(true);

        outputStream = null;
        isOpen = false;
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
        context.getPageHeader().setLastPage(isLastPage);
        context.getPageHeader().prepareForStorage();
        context.getPageHeader().setPageSize(recordCount);

        //replace
        recordCount = 0;
        byte[] bytes = json.writeValueAsBytes(context.getPageHeader());
        writeIntToStream(bytes.length, outputStream);
        outputStream.write(bytes);
        context.getColumnWriter().flushToOutputStream(outputStream);
    }
}