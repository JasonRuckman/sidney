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
import org.sidney.core.Bytes;
import org.sidney.core.Registrations;
import org.sidney.core.SidneyException;
import org.sidney.core.TypeRef;
import org.sidney.core.serde.serializer.Serializer;
import org.sidney.core.serde.serializer.SerializerContext;

import java.io.InputStream;

public abstract class BaseReader<T> {
    protected Registrations registrations;
    protected Class type;
    protected InputStream inputStream;
    protected ReadContext context;
    protected ObjectMapper json = new ObjectMapper();
    protected Serializer serializer;
    protected int recordCount = 0;
    protected SerializerContext builder;
    protected PageHeader currentPageHeader = null;
    protected boolean isOpen = false;

    public BaseReader(Registrations registrations, TypeRef typeRef) {
        this.registrations = registrations;
        this.builder = new SerializerContext(registrations);
        this.serializer = builder.serializer(typeRef);
    }

    /**
     * Check for new items
     *
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
     *
     * @return the next item
     */
    public T read() {
        if (!isOpen) {
            throw new SidneyException("Reader is not open.");
        }
        context.setColumnIndex(0);
        return (T) serializer.readValue(context);
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
            int numRead = inputStream.read(bytes);
            if (numRead < size) {
                throw new SidneyException("Could not fully read buffer.");
            }
            currentPageHeader = json.readValue(bytes, PageHeader.class);
            currentPageHeader.prepareForRead();
            ColumnReader reader = new ColumnReader();
            context = new ReadContext(
                reader
            );
            builder.finish(reader);
            recordCount = currentPageHeader.getPageSize();
            context.setPageHeader(currentPageHeader);
            context.getColumnReader().loadFromInputStream(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}