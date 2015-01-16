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
import org.sidney.core.encoding.io.StreamUtils;
import org.sidney.core.serde.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Reader<T> {
    private Class<T> type;
    private InputStream inputStream;
    private Class[] generics;
    private ReadContext context;
    private ObjectMapper json = new ObjectMapper();
    private TypeHandler typeHandler;
    private TypeReader typeReader = new TypeReader();
    private int recordCount = 0;
    private TypeHandlerFactory handlerFactory;
    private PageHeader currentPageHeader = null;
    private int totalCount = 0;

    Reader(Class type, InputStream inputStream, Registrations registrations) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.inputStream = inputStream;
        this.typeHandler = handlerFactory.handler(
                type, null, TypeUtil.binding(type)
        );
        try {
            loadNextPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Reader(Class type, InputStream inputStream, Registrations registrations, Class... generics) {
        this.handlerFactory = new TypeHandlerFactory(registrations);
        this.type = type;
        this.inputStream = inputStream;
        this.generics = generics;
        this.typeHandler = handlerFactory.handler(type, null, null, generics);
        try {
            loadNextPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

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

    public T read() {
        context.setColumnIndex(0);
        return (T) typeHandler.readValue(typeReader, context);
    }

    public List<T> readAll() {
        List<T> records = new ArrayList<>();
        while (hasNext()) {
            records.add(read());
        }
        return records;
    }

    private void loadNextPage() {
        try {
            int size = StreamUtils.readIntFromStream(inputStream);
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            currentPageHeader = json.readValue(bytes, PageHeader.class);
            currentPageHeader.prepareForRead();
            context = new ReadContext(
                    new ColumnReader(
                            typeHandler
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