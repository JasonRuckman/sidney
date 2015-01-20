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
import org.sidney.core.serde.*;
import org.sidney.core.serde.serializer.Serializer;
import org.sidney.core.serde.serializer.Serializers;
import org.sidney.core.serde.serializer.Types;

import java.io.InputStream;

/**
 * Reads out items from underlying streams
 * @param <T>
 */
public class Reader<T> {
    private InputStream inputStream;
    private ReadContext context;
    private ObjectMapper json = new ObjectMapper();
    private Serializer serializer;
    private TypeReader typeReader = new TypeReader();
    private int recordCount = 0;
    private Serializers handlerFactory;
    private PageHeader currentPageHeader = null;
    private boolean isOpen = false;

    Reader(Class type, Registrations registrations) {
        this.handlerFactory = new Serializers(registrations);
        this.serializer = handlerFactory.serializer(
                type, null, Types.binding(type)
        );
    }

    Reader(Class type, Registrations registrations, Class... generics) {
        this.handlerFactory = new Serializers(registrations);
        this.serializer = handlerFactory.serializer(type, null, null, generics);
    }

    /**
     * Check for new items
     * @return whether there are more items
     */
    public boolean hasNext() {
        if (currentPageHeader == null) {
            loadNextPage();
            return hasNext();
        }

        if (recordCount-- > 0) {
            return true;
        }

        if (!currentPageHeader.isLastPage()) {
            loadNextPage();
            return hasNext();
        }

        return false;
    }

    /**
     * Read the next item from the stream
     * @return the next item
     */
    public T read() {
        if(!isOpen) {
            throw new SidneyException("Reader is not open.");
        }
        context.setColumnIndex(0);
        return (T) serializer.readValue(typeReader, context);
    }

    /**
     * Open the given {@link java.io.InputStream} for reading.
     */
    public void open(InputStream inputStream) {
        this.inputStream = inputStream;
        this.recordCount = 0;
        this.isOpen = true;
    }

    /**
     * Marks this reader as closed
     */
    public void close() {
        inputStream = null;
        isOpen = false;
    }

    private void loadNextPage() {
        try {
            int size = Bytes.readIntFromStream(inputStream);
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            currentPageHeader = json.readValue(bytes, PageHeader.class);
            currentPageHeader.prepareForRead();
            context = new ReadContext(
                    new ColumnReader(
                            serializer
                    )
            );
            recordCount = currentPageHeader.getPageSize();
            context.setPageHeader(currentPageHeader);
            context.getColumnReader().loadFromInputStream(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}